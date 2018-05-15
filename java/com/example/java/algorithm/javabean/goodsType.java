package com.example.java.algorithm.javabean;

import cn.bmob.v3.BmobObject;

/**
 * author: DeDao233.
 * time: 2018/4/4.
 */

public class goodsType extends BmobObject {
    private String sort_sign;
    private String sort_name;

    public String getSort_sign() {
        return sort_sign;
    }

    public void setSort_sign(String sort_sign) {
        this.sort_sign = sort_sign;
    }

    public String getSort_name() {
        return sort_name;
    }

    public void setSort_name(String sort_name) {
        this.sort_name = sort_name;
    }
}
