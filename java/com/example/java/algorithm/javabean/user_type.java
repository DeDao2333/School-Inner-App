package com.example.java.algorithm.javabean;

import cn.bmob.v3.BmobObject;

/**
 * author: DeDao233.
 * time: 2018/4/4.
 */

public class user_type extends BmobObject {
    private String type_sign;
    private String type_name;

    public String getType_sign() {
        return type_sign;
    }

    public void setType_sign(String type_sign) {
        this.type_sign = type_sign;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }
}
