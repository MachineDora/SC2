package com.example.cinema.vo;


import com.example.cinema.po.Coupon;
import com.example.cinema.po.ScheduleItem;

import java.sql.Timestamp;
import java.util.List;

public class OrderFormWithMovieScheduleCouponVO {


    private int userId;

    private String movieName;

    private ScheduleItem scheduleItem;

    private Coupon coupon;

    private int orderformId;

    private int state;

    /**
     * 付款方式
     * 0: 银行卡 1: 会员卡
     */
    private int paypath;

    private int couponId;

    private Timestamp time;

    private List<TicketVO> tickets;

    private int ticketNumber;

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public ScheduleItem getScheduleItem() {
        return scheduleItem;
    }

    public void setScheduleItem(ScheduleItem scheduleItem) {
        this.scheduleItem = scheduleItem;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public List<TicketVO> getTickets(){
        return tickets;
    }

    public void setTickets(List<TicketVO> tickets){
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