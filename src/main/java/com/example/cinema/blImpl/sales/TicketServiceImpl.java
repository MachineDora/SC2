package com.example.cinema.blImpl.sales;

import com.example.cinema.bl.sales.TicketService;
import com.example.cinema.blImpl.management.hall.HallServiceForBl;
import com.example.cinema.blImpl.management.schedule.ScheduleServiceForBl;
import com.example.cinema.data.promotion.ActivityMapper;
import com.example.cinema.data.promotion.CouponMapper;
import com.example.cinema.data.promotion.VIPCardMapper;
import com.example.cinema.data.sales.TicketMapper;
import com.example.cinema.po.*;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liying on 2019/4/16.
 */
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    TicketMapper ticketMapper;
    @Autowired
    ScheduleServiceForBl scheduleService;
    @Autowired
    HallServiceForBl hallService;
    @Autowired
    CouponMapper couponMapper;
    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    VIPCardMapper vipCardMapper;
    @Override
    @Transactional
    public ResponseVO addTicket(TicketForm ticketForm) {
        try{
            List<Coupon> coupons = new ArrayList<>();
            List<TicketVO> ticketVOS = new ArrayList<>();
            List<SeatForm> seats = ticketForm.getSeats();
            double total = 0;
            for(int i=0;i<seats.size();i++) {
                Ticket ticket = new Ticket();
                ticket.setColumnIndex(seats.get(i).getColumnIndex());
                ticket.setRowIndex(seats.get(i).getRowIndex());
                ticket.setScheduleId(ticketForm.getScheduleId());
                ticket.setTime(new Timestamp(new Date().getTime()));
                ticket.setUserId(ticketForm.getUserId());
                ticket.setState(0);
                ticketMapper.insertTicket(ticket);
                ticketVOS.add(ticket.getVO());
                total +=  scheduleService.getScheduleItemById( ticket.getScheduleId()).getFare();
            }
            coupons = couponMapper.selectCouponByUser(ticketForm.getUserId());
            TicketWithCouponVO ticketWithCouponVO = new TicketWithCouponVO();
            ticketWithCouponVO.setTicketVOList(ticketVOS);
            ticketWithCouponVO.setCoupons(coupons);
            ticketWithCouponVO.setTotal(total);
            List<Activity> activities = activityMapper.selectActivitiesByMovie(scheduleService.getScheduleItemById(ticketForm.getScheduleId()).getMovieId());
            ticketWithCouponVO.setActivities(activities);
            return ResponseVO.buildSuccess(ticketWithCouponVO);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }

    @Override
    @Transactional
    public ResponseVO completeTicket(List<Integer> id, int couponId) {
        try {
            int movieid=0;
            double totalfare = 0;
            Ticket ticket1 ;
            if(couponId!=0) {
                Coupon coupon = couponMapper.selectById(couponId);
                Timestamp stime = coupon.getStartTime();
                Timestamp etime = coupon.getEndTime();
                Boolean can = true;
                for (int i = 0; i < id.size(); i++) {
                    Ticket ticket = ticketMapper.selectTicketById(id.get(i));
                    Timestamp timestamp = ticket.getTime();
                    movieid = scheduleService.getScheduleItemById(ticket.getScheduleId()).getMovieId();
                    if (timestamp.after(etime) || timestamp.before(stime)) {
                        can = false;
                        return ResponseVO.buildFailure("该优惠券不能在当前时间使用");
                    }
                    ScheduleItem schedule = scheduleService.getScheduleItemById(ticket.getScheduleId());
                    double fare = schedule.getFare();
                    totalfare += fare;
                }
                if (totalfare < coupon.getTargetAmount()) {
                    can = false;
                    return ResponseVO.buildFailure("所选商品总价不满足优惠券使用条件");
                }
                ticket1 = ticketMapper.selectTicketById(id.get(0));
                couponMapper.deleteCouponUser(couponId, ticket1.getUserId());
            }
            ticket1 = ticketMapper.selectTicketById(id.get(0));
            List<Activity> activities = activityMapper.selectActivitiesByMovie(movieid);
            Timestamp timestamp1 = ticket1.getTime();
            for(int i=0;i<activities.size();i++){
                if(timestamp1.after(activities.get(i).getStartTime())&&timestamp1.before(activities.get(i).getEndTime())){
                    couponMapper.insertCouponUser(activities.get(i).getCoupon().getId(),ticket1.getUserId());
                }
            }
            for(int i =0;i<id.size();i++){
                ticketMapper.updateTicketState(id.get(i),1);
            }
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getBySchedule(int scheduleId) {
        try {
            List<Ticket> tickets = ticketMapper.selectTicketsBySchedule(scheduleId);
            ScheduleItem schedule=scheduleService.getScheduleItemById(scheduleId);
            Hall hall=hallService.getHallById(schedule.getHallId());
            int[][] seats=new int[hall.getRow()][hall.getColumn()];
            tickets.stream().forEach(ticket -> {
                seats[ticket.getRowIndex()][ticket.getColumnIndex()]=1;
            });
            ScheduleWithSeatVO scheduleWithSeatVO=new ScheduleWithSeatVO();
            scheduleWithSeatVO.setScheduleItem(schedule);
            scheduleWithSeatVO.setSeats(seats);
            return ResponseVO.buildSuccess(scheduleWithSeatVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getTicketByUser(int userId) {
        try {
            List<Ticket> tickets= ticketMapper.selectTicketByUser(userId);
            List<TicketWithScheduleVO> ticketWithScheduleVOS=new ArrayList<>();
            for(int i=0;i<tickets.size();i++){
                Ticket ticket = tickets.get(i);
                TicketWithScheduleVO tws = new TicketWithScheduleVO();
                tws.setTime(ticket.getTime());
                tws.setSchedule(scheduleService.getScheduleItemById(ticket.getScheduleId()));
                tws.setUserId(ticket.getUserId());
                tws.setState(String.valueOf(ticket.getState()));
                tws.setRowIndex(ticket.getRowIndex());
                tws.setColumnIndex(ticket.getColumnIndex());
                tws.setId(ticket.getId());
                ticketWithScheduleVOS.add(tws);
            }
            return ResponseVO.buildSuccess(ticketWithScheduleVOS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }

    @Override
    @Transactional
    public ResponseVO completeByVIPCard(List<Integer> id, int couponId) {
        try {
            int movieid=0;
            double totalfare = 0;
            Ticket ticket1 ;
            if(couponId!=0) {
                Coupon coupon = couponMapper.selectById(couponId);
                Timestamp stime = coupon.getStartTime();
                Timestamp etime = coupon.getEndTime();
                Boolean can = true;
                for (int i = 0; i < id.size(); i++) {
                    Ticket ticket = ticketMapper.selectTicketById(id.get(i));
                    Timestamp timestamp = ticket.getTime();
                    movieid = scheduleService.getScheduleItemById(ticket.getScheduleId()).getMovieId();
                    if (timestamp.after(etime) || timestamp.before(stime)) {
                        can = false;
                        return ResponseVO.buildFailure("该优惠券不能在当前时间使用");
                    }
                    ScheduleItem schedule = scheduleService.getScheduleItemById(ticket.getScheduleId());
                    double fare = schedule.getFare();
                    totalfare += fare;
                }
                if (totalfare < coupon.getTargetAmount()) {
                    can = false;
                    return ResponseVO.buildFailure("所选商品总价不满足优惠券使用条件");
                }
                totalfare -= coupon.getDiscountAmount();
                ticket1 = ticketMapper.selectTicketById(id.get(0));
                VIPCard vipcard = vipCardMapper.selectCardByUserId(ticket1.getUserId());
                if (vipcard.getBalance() >= totalfare) {
                    vipCardMapper.updateCardBalance(vipcard.getId(), vipcard.getBalance() - totalfare);
                } else {
                    return ResponseVO.buildFailure("会员卡余额不足");
                }
                couponMapper.deleteCouponUser(couponId, ticket1.getUserId());
            }else{
                for (int i = 0; i < id.size(); i++) {
                    Ticket ticket = ticketMapper.selectTicketById(id.get(i));
                    ScheduleItem schedule = scheduleService.getScheduleItemById(ticket.getScheduleId());
                    double fare = schedule.getFare();
                    totalfare += fare;
                }
                ticket1 = ticketMapper.selectTicketById(id.get(0));
                VIPCard vipcard = vipCardMapper.selectCardByUserId(ticket1.getUserId());
                if (vipcard.getBalance() >= totalfare) {
                    vipCardMapper.updateCardBalance(vipcard.getId(), vipcard.getBalance() - totalfare);
                } else {
                    return ResponseVO.buildFailure("会员卡余额不足");
                }
            }
            List<Activity> activities = activityMapper.selectActivitiesByMovie(movieid);
            Timestamp timestamp1 = ticket1.getTime();
            for(int i=0;i<activities.size();i++){
                if(timestamp1.after(activities.get(i).getStartTime())&&timestamp1.before(activities.get(i).getEndTime())){
                    couponMapper.insertCouponUser(activities.get(i).getCoupon().getId(),ticket1.getUserId());
                }
            }
            for(int i =0;i<id.size();i++){
                ticketMapper.updateTicketState(id.get(i),1);
            }
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO cancelTicket(List<Integer> id) {
        try {
            for(int i=0;i<id.size();i++) {
                if(ticketMapper.selectTicketById(id.get(i)).getState()==0 ) {
                    ticketMapper.updateTicketState(id.get(i), 2);
                }
            }
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }



}
