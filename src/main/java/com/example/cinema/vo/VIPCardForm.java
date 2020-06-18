package com.example.cinema.vo;

/**
 * Created by liying on 2019/4/14.
 */
public class VIPCardForm {

    /**
     * vip卡id
     */
    private int vipId;

    /**
     * 付款金额
     */
    private int amount;

    private  String name;

    private  double sum;


    public int getVipId() {
        return vipId;
    }

    public void setVipId(int vipId) {
        this.vipId = vipId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }

    public void setSum(double sum){
        this.sum=sum;
    }

    public double getSum(){
        return sum;
    }
}
