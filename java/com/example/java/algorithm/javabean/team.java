package com.example.java.algorithm.javabean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * author: DeDao233.
 * time: 2018/4/4.
 */

public class team extends BmobObject {
    private String content;
    private String cur_peo;
    private String max_peo;
    private String place;
    private BmobDate time_start;
    private String title;
    private _User username;

    public _User getUsername() {
        return username;
    }

    public void setUsername(_User username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCur_peo() {
        return cur_peo;
    }

    public void setCur_peo(String cur_peo) {
        this.cur_peo = cur_peo;
    }

    public String getMax_peo() {
        return max_peo;
    }

    public void setMax_peo(String max_peo) {
        this.max_peo = max_peo;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public BmobDate getTime_start() {
        return time_start;
    }

    public void setTime_start(BmobDate time_start) {
        this.time_start = time_start;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
