package com.example.cinema.vo;

import com.example.cinema.po.VIPInfo;

/**
 * Created by liying on 2019/4/15.
 */
public class VipForm {

    int id;

    String name;

    String description;

    double price;

    /**
     * 优惠所满钱
     */
    private double money1;

    /**
     * 优惠所送钱
     */
    private double money2;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) {this.name=name;}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getMoney1() { return money1; }

    public void setMoney1(double money1) { this.money1 = money1; }

    public double getMoney2() { return money2; }

    public void setMoney2(double money2) { this.money2 = money2; }
}
