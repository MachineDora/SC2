package com.example.cinema.bl.sales;

import com.example.cinema.vo.ResponseVO;

import java.util.List;

public interface OrderFormService {

    ResponseVO addOrderForm(List<Integer> ticketid, int couponId);

    ResponseVO addOrderFormByVipCard(List<Integer> ticketid, int couponId);

    ResponseVO refund(int orderFormId);

    ResponseVO getOrderFormByUser(int userId);
    ResponseVO getOrderFormById(int Id);

}
