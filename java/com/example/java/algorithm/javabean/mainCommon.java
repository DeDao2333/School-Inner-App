package com.example.java.algorithm.javabean;

import cn.bmob.v3.BmobObject;

/**
 * author: DeDao233.
 * time: 2018/5/8.
 */
public class mainCommon extends BmobObject {
    private String content;
    private _User author;
    private mainPost post;

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

    public mainPost getPost() {
        return post;
    }

    public void setPost(mainPost post) {
        this.post = post;
    }
}
