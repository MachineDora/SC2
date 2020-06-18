package com.example.cinema.vo;


import java.sql.Timestamp;
import java.util.List;

public class OrderFormWithMovieVO {


    private int userId;

    private String movieName;

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
