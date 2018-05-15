package com.example.java.algorithm.activity;

import com.example.java.algorithm.javabean._User;

import cn.bmob.newim.listener.BmobListener1;
import cn.bmob.v3.exception.BmobException;

/**
 * @author :smile
 * @project:QueryUserListener
 * @date :2016-02-01-16:23
 */
public abstract class QueryUserListener extends BmobListener1<_User> {

    public abstract void done(_User s, BmobException e);

    @Override
    protected void postDone(_User o, BmobException e) {
        done(o, e);
    }
}
