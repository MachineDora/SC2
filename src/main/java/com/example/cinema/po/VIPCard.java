package com.example.cinema.po;


import java.sql.Timestamp;

/**
 * Created by liying on 2019/4/14.
 */

public class VIPCard {

    private int kind;

    private String name;

    private double price;

    private String description;

    private double money1;

    private double money2;

    /**
     * 用户id
     */
    private int userId;

    /**
     * 会员卡id
     */
    private int id;

    /**
     * 会员卡余额
     */
    private double balance;

    /**
     * 办卡日期
     */
    private Timestamp joinDate;

    private String chargeHistory;


    public VIPCard() {

    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getMoney1 () {
        return money1;
    }

    public void setMoney1(double money1) {
        this.money1 = money1;
    }

    public double getMoney2 () {
        return money2;
    }

    public void setMoney2(double money2) {
        this.money2 = money2;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Timestamp getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Timestamp joinDate) {
        this.joinDate = joinDate;
    }

    public String getChargeHistory() {
        return chargeHistory;
    }

    public void setChargeHistory(String chargeHistory) {
        this.chargeHistory = chargeHistory;
    }

    public double calculate(double amount) {
        return (int)(amount/money1)*money2+amount;

    }
}
