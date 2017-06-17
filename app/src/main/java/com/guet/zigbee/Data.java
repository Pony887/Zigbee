package com.guet.zigbee;

/**
 * Created by 尹文强 on 2017/5/11.
 * 从网站获取到的数据，http协议，json解析
 * eee
 */

public class Data {
    private String id;
    private String name;
    private String version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVesdion(String version) {
        this.version = version;
    }
}
