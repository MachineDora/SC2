package com.example.cinema.blImpl.sales;

import com.example.cinema.bl.sales.OrderFormService;
import com.example.cinema.data.management.RefundMapper;
import com.example.cinema.data.management.ScheduleMapper;
import com.example.cinema.data.promotion.CouponMapper;
import com.example.cinema.data.promotion.VIPCardMapper;
import com.example.cinema.data.sales.OrderFormMapper;
import com.example.cinema.data.sales.TicketMapper;
import com.example.cinema.po.*;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class OrderFormServiceImpl implements OrderFormService {
    @Autowired
    OrderFormMapper orderFormMapper;
    @Autowired
    TicketMapper ticketMapper;
    @Autowired
    ScheduleMapper scheduleMapper;
    @Autowired
    CouponMapper couponMapper;
    @Autowired
    VIPCardMapper vipCardMapper;
    @Autowired
    RefundMapper refundMapper;
    @Override
    public ResponseVO addOrderForm(List<Integer> ticketid, int couponId) {
        try{
            OrderForm orderForm = new OrderForm();
            List<Ticket> tickets = new ArrayList<>();
            for(int i=0;i<ticketid.size();i++){
                tickets.add(ticketMapper.selectTicketById(ticketid.get(i)));
            }
            Ticket ticket1 = ticketMapper.selectTicketById(ticketid.get(0));
            orderForm.setTime(new Timestamp(new Date().getTime()));
            orderForm.setTickets(tickets);
            orderForm.setUserId(ticket1.getUserId());
            orderForm.setPaypath(0);
            orderForm.setCouponId(couponId);
            orderForm.setState(0);
            if(ticket1.getState()==1) {
                orderFormMapper.insertOrderForm(orderForm);
                orderFormMapper.insertOrderFormAndTickets(orderForm.getOrderformId(),ticketid);
                return ResponseVO.buildSuccess();
            }else{
                return ResponseVO.buildFailure("订单创建失败，因为您还未付款");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO addOrderFormByVipCard(List<Integer> ticketid, int couponId) {
        try{
            OrderForm orderForm = new OrderForm();
            List<Ticket> tickets = new ArrayList<>();
            for(int i=0;i<ticketid.size();i++){
                tickets.add(ticketMapper.selectTicketById(ticketid.get(i)));
            }
            Ticket ticket1 = ticketMapper.selectTicketById(ticketid.get(0));
            orderForm.setTime(new Timestamp(new Date().getTime()));
            orderForm.setTickets(tickets);
            orderForm.setUserId(ticket1.getUserId());
            orderForm.setPaypath(1);
            orderForm.setCouponId(couponId);
            orderForm.setState(0);
            if(ticket1.getState()==1) {
                orderFormMapper.insertOrderForm(orderForm);
                orderFormMapper.insertOrderFormAndTickets(orderForm.getOrderformId(),ticketid);
                return ResponseVO.buildSuccess();
            }else{
                return ResponseVO.buildFailure("订单创建失败，因为您还未付款");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO refund(int orderFormId) {
        try{
            OrderForm orderForm = orderFormMapper.selectOrderFormById(orderFormId);
            if(orderForm.getState()==1){
                return  ResponseVO.buildFailure("该订单已完成退款，不可重复退款");
            }
            if(orderForm.getPaypath()==0) {
                orderFormMapper.updateOrderFormState(orderFormId, 1);
                return ResponseVO.buildSuccess();
            }
            else{
                double refund = 0;
                int ticketNumber = orderForm.getTickets().size();
                Ticket ticket1 = orderForm.getTickets().get(0);
                Coupon coupon = couponMapper.selectById(orderForm.getCouponId());
                if(coupon==null){
                    refund = (scheduleMapper.selectScheduleById(ticket1.getScheduleId()).getFare()) * ticketNumber;
                }else {
                    refund = (scheduleMapper.selectScheduleById(ticket1.getScheduleId()).getFare()) * ticketNumber - coupon.getDiscountAmount();
                }
                VIPCard vipcard = vipCardMapper.selectCardByUserId(ticket1.getUserId());
                Refund refund1 = refundMapper.getLatestRefund();
                if(refund1!=null) {
                    Timestamp timeNow = new Timestamp(new Date().getTime());
                    Timestamp timeMovie = new Timestamp(scheduleMapper.selectScheduleById(orderFormMapper.selectOrderFormById(orderFormId).getTickets().get(0).getScheduleId()).getStartTime().getTime());
                    int n = (int) (timeMovie.getTime() - timeNow.getTime()) / (1000 * 60 * 60);
                    int timeOne = refund1.getTimeOne();
                    int timeTwo = refund1.getTimeTwo();
                    int deadline = refund1.getDeadline();
                    if (n < deadline) {
                        return ResponseVO.buildFailure("此订单已超过退票最后时间，无法退票");
                    } else if (n < timeTwo & n >= deadline) {
                        double deadlinePercent = refund1.getDeadlinePercent();
                        vipCardMapper.updateCardBalance(vipcard.getId(), vipcard.getBalance() + refund * deadlinePercent);
                        orderFormMapper.updateOrderFormState(orderFormId, 1);
                        return ResponseVO.buildSuccess("退款成功");
                    } else if (n < timeOne & n >= timeTwo) {
                        double timeTwoPercent = refund1.getTimeTwoPercent();
                        vipCardMapper.updateCardBalance(vipcard.getId(), vipcard.getBalance() + refund * timeTwoPercent);
                        orderFormMapper.updateOrderFormState(orderFormId, 1);
                        return ResponseVO.buildSuccess("退款成功");
                    } else {
                        double timeOnePercent = refund1.getTimeOnePercent();
                        vipCardMapper.updateCardBalance(vipcard.getId(), vipcard.getBalance() + refund * timeOnePercent);
                        orderFormMapper.updateOrderFormState(orderFormId, 1);
                        return ResponseVO.buildSuccess("退款成功");
                    }
                }else{
                    Timestamp timeNow = new Timestamp(new Date().getTime());
                    Timestamp timeMovie = new Timestamp(scheduleMapper.selectScheduleById(orderFormMapper.selectOrderFormById(orderFormId).getTickets().get(0).getScheduleId()).getStartTime().getTime());
                    int n = (int) (timeMovie.getTime() - timeNow.getTime());
                    if(n>0) {
                        vipCardMapper.updateCardBalance(vipcard.getId(), vipcard.getBalance() + refund);
                        orderFormMapper.updateOrderFormState(orderFormId, 1);
                        return ResponseVO.buildSuccess("退款成功");
                    }else{
                        return ResponseVO.buildFailure("该电影已上映，无法退款");
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("退款失败");
        }
    }

    @Override
    public ResponseVO getOrderFormByUser(int userId){
        try {
            List<OrderForm> orderForms = orderFormMapper.selectOrderFormByUserId(userId);
            if(orderForms.size()==0){
                return ResponseVO.buildSuccess("您还没有购买过电影票哦 ");
            }
            else {
                String movieName = scheduleMapper.selectScheduleById(orderForms.get(0).getTickets().get(0).getScheduleId()).getMovieName();
                List<OrderFormWithMovieVO> orderFormVOS = new ArrayList<>();
                for (int i = 0; i < orderForms.size(); i++) {
                    OrderFormWithMovieVO orderFormWithMovieVO = new OrderFormWithMovieVO();
                    orderFormWithMovieVO.setCouponId(orderForms.get(i).getCouponId());
                    orderFormWithMovieVO.setOrderformId(orderForms.get(i).getOrderformId());
                    orderFormWithMovieVO.setPaypath(orderForms.get(i).getPaypath());
                    orderFormWithMovieVO.setState(orderForms.get(i).getState());
                    List<TicketVO> ticketVOS = new ArrayList<>();
                    for (int j = 0; j < orderForms.get(i).getTickets().size(); j++) {
                        ticketVOS.add(orderForms.get(i).getTickets().get(j).getVO());
                    }
                    orderFormWithMovieVO.setTicketNumber(ticketVOS.size());
                    orderFormWithMovieVO.setTickets(ticketVOS);
                    orderFormWithMovieVO.setMovieName(movieName);
                    orderFormWithMovieVO.setTime(orderForms.get(i).getTime());
                    orderFormWithMovieVO.setUserId(orderForms.get(i).getUserId());
                    orderFormVOS.add(orderFormWithMovieVO);
                }
                return ResponseVO.buildSuccess(orderFormVOS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }
    public ResponseVO getOrderFormById(int Id){
        try {
            OrderForm orderForm = orderFormMapper.selectOrderFormById(Id);
            String movieName = scheduleMapper.selectScheduleById(orderForm.getTickets().get(0).getScheduleId()).getMovieName();
            OrderFormWithMovieScheduleCouponVO orderFormWithMovieScheduleCouponVO = new OrderFormWithMovieScheduleCouponVO();
            orderFormWithMovieScheduleCouponVO.setCouponId(orderForm.getCouponId());
            orderFormWithMovieScheduleCouponVO.setOrderformId(orderForm.getOrderformId());
            orderFormWithMovieScheduleCouponVO.setPaypath(orderForm.getPaypath());
            orderFormWithMovieScheduleCouponVO.setState(orderForm.getState());
            List<TicketVO> ticketVOS = new ArrayList<>();
            for(int j = 0;j<orderForm.getTickets().size();j++){
                ticketVOS.add(orderForm.getTickets().get(j).getVO());
            }
            orderFormWithMovieScheduleCouponVO.setTicketNumber(ticketVOS.size());
            orderFormWithMovieScheduleCouponVO.setTickets(ticketVOS);
            orderFormWithMovieScheduleCouponVO.setScheduleItem(scheduleMapper.selectScheduleById(orderForm.getTickets().get(0).getScheduleId()));
            orderFormWithMovieScheduleCouponVO.setMovieName(movieName);
            orderFormWithMovieScheduleCouponVO.setTime(orderForm.getTime());
            orderFormWithMovieScheduleCouponVO.setCoupon(couponMapper.selectById(orderForm.getCouponId()));
            orderFormWithMovieScheduleCouponVO.setUserId(orderForm.getUserId());
            return ResponseVO.buildSuccess(orderFormWithMovieScheduleCouponVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }
}
