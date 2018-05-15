package com.example.java.algorithm.model;

import com.example.java.algorithm.javabean._User;

import cn.bmob.v3.BmobObject;

/**
 * Created by 59575 on 2018/2/23.
 */

public class SingleUser extends BmobObject {
    private static _User mUser;

    public static void setUser(_User myUser) {
        mUser=myUser;
    }

    public static _User getUser() {
        return mUser;
    }

}
