package com.example.java.algorithm.javabean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * author: DeDao233.
 * time: 2018/4/3.
 */

public class _User extends BmobUser {

    private user_type mUser_type;
    private String nickname;
    private String major;
    private BmobFile headPic;
    private String sno;
    private BmobRelation likes_main;
    private BmobRelation likes_sh;

    public _User() {
//        nickname = "null";
    }

    public BmobRelation getLikes_main() {
        return likes_main;
    }

    public void setLikes_main(BmobRelation likes_main) {
        this.likes_main = likes_main;
    }

    public BmobRelation getLikes_sh() {
        return likes_sh;
    }

    public void setLikes_sh(BmobRelation likes_sh) {
        this.likes_sh = likes_sh;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public BmobFile getHeadPic() {
        return headPic;
    }

    public void setHeadPic(BmobFile headPic) {
        this.headPic = headPic;
    }

    public user_type getUser_type() {
        return mUser_type;
    }

    public void setUser_type(user_type user_type) {
        mUser_type = user_type;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}
