package com.example.java.algorithm.Test;

import com.example.java.algorithm.javabean._User;
import com.example.java.algorithm.javabean.goodsType;
import com.example.java.algorithm.javabean.user_type;

/**
 * author: DeDao233.
 * time: 2018/5/4.
 */
public class TestData {

    private static _User mTestUser = new _User();
    private static goodsType mGoodType;
    private static user_type mUserType;

    public static _User getTestUser() {
        mTestUser.setObjectId("vw2KFFFT");
        return mTestUser;
    }

    public static goodsType getmGoodType() {
        mGoodType = new goodsType();
        mGoodType.setObjectId("meAb4449");
        return mGoodType;
    }

    public static user_type getmUserType() {
        mUserType = new user_type();
        mUserType.setObjectId("d0fh6668");
        return mUserType;
    }
}

