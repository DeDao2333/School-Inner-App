package com.example.java.algorithm.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.ninegrid.NineGridView;

/**
 * author: DeDao233.
 * time: 2018/5/4.
 */
public class EditPageBaseAc extends BaseActivity {

//    private IUpLoadFile mIUpLoadFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NineGridView.setImageLoader(new GlideImageLoader());
    }

    public void upLoad(IUpLoadFile i) {
        i.upLoadFils();
    }

    interface IUpLoadFile {
        void upLoadFils();
    }

    /**
     * Picasso 加载
     */
    class GlideImageLoader implements NineGridView.ImageLoader {

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
