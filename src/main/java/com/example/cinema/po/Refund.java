package com.example.cinema.po;

import com.example.cinema.vo.RefundVO;

public class Refund {
    private int timeOne;
    private int timeTwo;
    private int deadline;
    private double timeOnePercent;
    private double timeTwoPercent;
    private double deadlinePercent;

    public double getDeadlinePercent() {
        return deadlinePercent;
    }

    public double getTimeTwoPercent() {
        return timeTwoPercent;
    }

    public double getTimeOnePercent() {
        return timeOnePercent;
    }

    public int getTimeOne() {
        return timeOne;
    }

    public int getTimeTwo() {
        return timeTwo;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public void setDeadlinePercent(double deadlinePercent) {
        this.deadlinePercent = deadlinePercent;
    }

    public void setTimeOne(int timeOne) {
        this.timeOne = timeOne;
    }

    public void setTimeOnePercent(double timeOnePercent) {
        this.timeOnePercent = timeOnePercent;
    }

    public void setTimeTwo(int timeTwo) {
        this.timeTwo = timeTwo;
    }

    public void setTimeTwoPercent(double timeTwoPercent) {
        this.timeTwoPercent = timeTwoPercent;
    }

    public RefundVO getRefundVO(){
        RefundVO refundVO = new RefundVO();
        refundVO.setDeadline(this.deadline);
        refundVO.setDeadlinePercent((float)this.deadlinePercent);
        refundVO.setTimeOne(this.timeOne);
        refundVO.setTimeOnePercent((float)this.timeOnePercent);
        refundVO.setTimeTwo(this.timeTwo);
        refundVO.setTimeTwoPercent((float)this.timeTwoPercent);
        return refundVO;
    }
}
