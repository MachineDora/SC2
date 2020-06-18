package com.example.cinema.vo;

public class RefundVO {
    private int timeOne;
    private int timeTwo;
    private int deadline;
    private float timeOnePercent;
    private float timeTwoPercent;
    private float deadlinePercent;

    public float getDeadlinePercent() {
        return deadlinePercent;
    }

    public float getTimeTwoPercent() {
        return timeTwoPercent;
    }

    public float getTimeOnePercent() {
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

    public void setDeadlinePercent(float deadlinePercent) {
        this.deadlinePercent = deadlinePercent;
    }

    public void setTimeOne(int timeOne) {
        this.timeOne = timeOne;
    }

    public void setTimeOnePercent(float timeOnePercent) {
        this.timeOnePercent = timeOnePercent;
    }

    public void setTimeTwo(int timeTwo) {
        this.timeTwo = timeTwo;
    }

    public void setTimeTwoPercent(float timeTwoPercent) {
        this.timeTwoPercent = timeTwoPercent;
    }
}
