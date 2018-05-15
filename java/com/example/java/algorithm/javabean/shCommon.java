package com.example.java.algorithm.javabean;

import cn.bmob.v3.BmobObject;

/**
 * author: DeDao233.
 * time: 2018/5/8.
 */
public class shCommon extends BmobObject {
    private _User author;
    private String content;
    private shPost post;

    public _User getAuthor() {
        return author;
    }

    public void setAuthor(_User author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public shPost getPost() {
        return post;
    }

    public void setPost(shPost post) {
        this.post = post;
    }
}
