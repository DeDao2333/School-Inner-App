package com.example.java.algorithm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.java.algorithm.R;
import com.example.java.algorithm.Test.TestData;
import com.example.java.algorithm.javabean.mainPost;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

public class MainPostEditAc extends AppCompatActivity {


    @BindView(R.id.textEdit_mainpost_content)
    TextInputEditText mTextEditMainpostContent;
    @BindView(R.id.ninegridview_mainpost_edit)
    NineGridView mNinegridviewMainpostEdit;
    @BindView(R.id.button_mainpost_addPic)
    Button mButtonMainpostAddPic;
    @BindView(R.id.button_mainpost_finish)
    Button mButtonMainpostFinish;

    private List<LocalMedia> selectList;
    private List<String> mPicList = new ArrayList<>();
    private NineGridViewClickAdapter nineGridViewClickAdapter;
    private ArrayList<ImageInfo> imageInfo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sub);
        ButterKnife.bind(this);

        nineGridViewClickAdapter = new NineGridViewClickAdapter(this, imageInfo);
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

                    mPicList.clear();

                    for (LocalMedia pic : selectList) {
                        mPicList.add(pic.getCompressPath());
                    }

                    imageInfo.clear();

                    if (mPicList != null) {
                        for (String imageDetail : mPicList) {
                            ImageInfo info = new ImageInfo();
                            info.setThumbnailUrl(imageDetail);
                            info.setBigImageUrl(imageDetail);
                            imageInfo.add(info);
                        }
                    }
                    nineGridViewClickAdapter.setImageInfoList(imageInfo);
                    mNinegridviewMainpostEdit.setAdapter(nineGridViewClickAdapter);
                    break;
            }
        }
    }

    @OnClick({R.id.button_mainpost_addPic, R.id.button_mainpost_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_mainpost_addPic:
                // 进入相册 以下是例子：用不到的api可以不写
                PictureSelector.create(MainPostEditAc.this)
                        .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .selectionMedia(selectList)// 是否传入已选图片 List<LocalMedia> list
                        .compress(true)// 是否压缩 true or false
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                break;
            case R.id.button_mainpost_finish:

                if (selectList.size() != 0) {
                    final String[] filesPath = new String[selectList.size()];

                    for (int i = 0; i < mPicList.size(); i++) {
                        filesPath[i] = mPicList.get(i);
                    }

                    BmobFile.uploadBatch(filesPath, new UploadBatchListener() {
                        @Override
                        public void onSuccess(List<BmobFile> files, List<String> urls) {
                            //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                            //2、urls-上传文件的完整url地址
                            if (urls.size() == filesPath.length) {//如果数量相等，则代表文件全部上传完成
                                //do something
                                mainPost mainPost = new mainPost();
                                mainPost.setContent(mTextEditMainpostContent.getText().toString());
                                mainPost.setAuthor(TestData.getTestUser());
                                mainPost.setUser_type(TestData.getmUserType());
                                mainPost.setPicUrl(urls);
                                mainPost.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if (e == null) {
                                            Log.d("wer", "shpost upload succeed");
                                        } else {
                                            Log.d("wer", "shpost upload failed_  " + e);
                                        }
                                    }
                                });
                                Toast.makeText(MainPostEditAc.this, "upload succeed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onProgress(int i, int i1, int i2, int i3) {
                            Log.d("wer", "" + i1);
                        }

                        @Override
                        public void onError(int i, String s) {
                            Log.d("wer", "" + s);

                        }
                    });
                }
                finish();
                break;
        }
    }
}
