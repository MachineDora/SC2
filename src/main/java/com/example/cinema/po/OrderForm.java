package com.example.cinema.po;

import java.sql.Timestamp;


import java.util.List;

public class OrderForm {
    /**
     * 0已完成，1已退款
     */

    private int state;

    private int userId;

    private int orderformId;

    /**
     * 付款方式
     * 0: 银行卡 1: 会员卡
     */
    private int paypath;

    private int couponId;

    private Timestamp time;

    private List<Ticket> tickets;

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public int getUserId(){
        return userId;
    }


    public int getCouponId(){
        return couponId;
    }

    public void setCouponId(int couponId){
        this.couponId = couponId;
    }

    public List<Ticket> getTickets(){
        return tickets;
    }

    public void setTickets(List<Ticket> tickets){
        this.tickets = tickets;
    }

    public int getPaypath(){
        return  paypath;
    }

    public void setPaypath(int paypath){
        this.paypath = paypath;
    }

    public int getOrderformId(){
        return orderformId;
    }

    public void setOrderformId(int orderformId) {
        this.orderformId = orderformId;
    }
}
