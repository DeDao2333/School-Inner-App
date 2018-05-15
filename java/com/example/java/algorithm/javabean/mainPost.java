package com.example.java.algorithm.javabean;

import java.util.List;

import cn.bmob.v3.datatype.BmobRelation;

/**
 * author: DeDao233.
 * time: 2018/4/4.
 */

public class mainPost extends BasePost {
    private String content;
    private _User author;
    private user_type mUser_type;
    private List<String> mPicUrl;
    private Integer praiseSum;
    private Integer likeSum;
    private BmobRelation likes;

    public mainPost() {
        praiseSum=0;
        likeSum = 0;
    }

    public Integer getPraiseSum() {
        return praiseSum;
    }

    public void setPraiseSum(Integer praiseSum) {
        this.praiseSum = praiseSum;
    }

    public Integer getLikeSum() {
        return likeSum;
    }

    public void setLikeSum(Integer likeSum) {
        this.likeSum = likeSum;
    }

    public BmobRelation getLikes() {
        return likes;
    }

    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }

    @Override
    public List<String> getPicUrl() {
        return mPicUrl;
    }

    public void setPicUrl(List<String> picUrl) {
        mPicUrl = picUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public _User getAuthor() {
        return author;
    }

    public void setAuthor(_User author) {
        this.author = author;
    }

    public user_type getUser_type() {
        return mUser_type;
    }

    public void setUser_type(user_type user_type) {
        mUser_type = user_type;
    }
}
