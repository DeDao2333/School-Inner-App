package com.example.java.algorithm.tool;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.example.java.algorithm.javabean.BasePost;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author: DeDao233.
 * time: 2018/4/28.
 */
public class Tool {

    //动态申请权限
    public static void requestPower(Activity activity, String request) {
        //判断是否已经赋予权限
        if (ContextCompat.checkSelfPermission(activity,
                request)
                != PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    request)) {//这里可以写个对话框之类的项向用户解释为什么要申请权限，
                // 并在对话框的确认键后续再次申请权限
            } else {
                //申请权限，字符串数组内是一个或多个要申请的权限，
                // 1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                ActivityCompat.requestPermissions(activity,
                        new String[]{request,}, 1);
            }
        }
    }

    //设置九宫格图片
    public static void setNineViewPic(BasePost basePost, Context context, NineGridView nineGridView) {
        ArrayList<ImageInfo> imageInfo = new ArrayList<>();
        List<String> imageDetails = new ArrayList<>();

        for (String s : basePost.getPicUrl()) {
            imageDetails.add(s);
        }

        if (imageDetails != null) {
            for (String imageDetail : imageDetails) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(imageDetail);
                info.setBigImageUrl(imageDetail);
                imageInfo.add(info);
            }
        }
        nineGridView.setAdapter(new NineGridViewClickAdapter(context, imageInfo));
    }
}
