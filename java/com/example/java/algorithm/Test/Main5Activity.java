package com.example.java.algorithm.Test;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.java.algorithm.R;
import com.example.java.algorithm.activity.BaseActivity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

public class Main5Activity extends BaseActivity{

    Button button1,button2;
    BmobFile bmobFile;
    String picPath = "/storage/emulated/0/DCIM/Camera/IMG_20180417_201534.jpg";
    List<String> mPicList = new ArrayList<>();
    NineGridViewClickAdapter nineGridViewClickAdapter;
    NineGridView nineGridView;
    ArrayList<ImageInfo> imageInfo = new ArrayList<>();
    private List<LocalMedia> selectList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
            }
        }

        button1 = (Button) findViewById(R.id.button_1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 进入相册 以下是例子：用不到的api可以不写
                PictureSelector.create(Main5Activity.this)
                        .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .selectionMedia(selectList)// 是否传入已选图片 List<LocalMedia> list
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
            }
        });


        NineGridView.setImageLoader(new GlideImageLoader());

        nineGridViewClickAdapter = new NineGridViewClickAdapter(this, imageInfo);
        nineGridView = (NineGridView) findViewById(R.id.ninegridview);

        button2 = (Button) findViewById(R.id.button_2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的

//                    mBaseAdapter.setMediaListz(selectList);
//                    mBaseAdapter.notifyDataSetChanged();
                    mPicList.clear();

                    for(LocalMedia pic:selectList){
                        mPicList.add(pic.getPath());
                        Log.d("wer", pic.getPath());
                    }

                    Log.d("wer", "" + mPicList.size());

                    imageInfo.clear();

                    if ( mPicList!= null) {
                        for (String imageDetail : mPicList) {
                            ImageInfo info = new ImageInfo();
                            info.setThumbnailUrl(imageDetail);
                            info.setBigImageUrl(imageDetail);
                            imageInfo.add(info);
                        }
                    }
                    nineGridViewClickAdapter.setImageInfoList(imageInfo);
                    nineGridView.setAdapter(nineGridViewClickAdapter);
                    break;
            }
        }
    }

    /** Picasso 加载 */
    private class GlideImageLoader implements NineGridView.ImageLoader {

        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
            Glide.with(context)
                    .load(url)//
                    .into(imageView);
        }

        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }



}
