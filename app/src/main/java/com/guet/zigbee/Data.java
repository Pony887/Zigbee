package com.guet.zigbee;

/**
 * Created by 尹文强 on 2017/5/11.
 * 从网站获取到的数据，http协议，json解析
 */

public class Data {
    private String heart;
    private String x;
    private String y;
    private String bloodhigh;
    private String bloodlow;
    public String getHeart() {
        return heart;
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

    public String getBloodhigh() {
        return bloodhigh;
    }

    public String getBloodlow() {
        return bloodlow;
    }
}