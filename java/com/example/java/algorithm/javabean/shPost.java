package com.example.java.algorithm.javabean;

import java.util.List;

import cn.bmob.v3.datatype.BmobRelation;

/**
 * author: DeDao233.
 * time: 2018/4/4.
 */

public class shPost extends BasePost {
    private String content;
    private String price;
    private _User author;
    private goodsType sort_sign;
    private List<String> pics;
    private Integer praiseSum;
    private Integer likeSum;
    private BmobRelation likes;

    public shPost() {
        praiseSum = 0;
        likeSum=0;
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
        return pics;
    }

    public void setPicUrl(List<String> pics) {
        this.pics = pics;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public _User getAuthor() {
        return author;
    }

    public void setAuthor(_User author) {
        this.author = author;
    }

    public goodsType getSort_sign() {
        return sort_sign;
    }

    public void setSort_sign(goodsType sort_sign) {
        this.sort_sign = sort_sign;
    }
}
