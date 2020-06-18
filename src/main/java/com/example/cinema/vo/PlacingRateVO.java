package com.example.cinema.vo;

import java.util.Date;

public class PlacingRateVO {
    //某日电影总的上座率
    private double placingrate;
    //日期
    private Date date;

    public double getPlacingrate() {
        return placingrate;
    }

    public void setPlacingrate(double placingrate) {
        this.placingrate = placingrate;
    }

    public Date getdate() {
        return date;
    }

    public void setdate(Date date) {
        this.date = date;
    }
}
