package com.guet.zigbee;

/**
 * Javabeans类，获取到的数据
 * Created by 尹文强 on 2017/5/11.
 * 从网站获取到的数据
 */

public class Data {
    private String heart;
    private String x;
    private String y;
    private int bloodhigh;
    private int bloodlow;
    private String call;

    public String getHeart() {
        return heart;
    }

    public void setHeart(String heart) {
        this.heart = heart;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public int getBloodhigh() {
        return bloodhigh;
    }

    public void setBloodhigh(int bloodhigh) {
        this.bloodhigh = bloodhigh;
    }

    public int getBloodlow() {
        return bloodlow;
    }

    public void setBloodlow(int bloodlow) {
        this.bloodlow = bloodlow;
    }

    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
    }
}