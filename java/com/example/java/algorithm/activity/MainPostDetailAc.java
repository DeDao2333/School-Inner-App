package com.example.java.algorithm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.java.algorithm.R;
import com.example.java.algorithm.adapter.MainPostComAdapter;
import com.example.java.algorithm.javabean.mainCommon;
import com.example.java.algorithm.javabean.mainPost;
import com.example.java.algorithm.model.SingleUser;
import com.example.java.algorithm.tool.Tool;
import com.like.LikeButton;
import com.lzy.ninegrid.NineGridView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class MainPostDetailAc extends ItemBaseAc<MainPostComAdapter, mainCommon> {

    @BindView(R.id.image_userPic_mainPost)
    ImageView mImageUserPicMainPost;
    @BindView(R.id.tv_username_mainPost)
    TextView mTvUsernameMainPost;
    @BindView(R.id.tv_time_mainPost)
    TextView mTvTimeMainPost;
    @BindView(R.id.tv_content_mainPost)
    TextView mTvContentMainPost;
    @BindView(R.id.ninegridview_mainPost)
    NineGridView mNinegridviewMainPost;
    @BindView(R.id.likeBt_mainpost_heart)
    LikeButton mLikeBtMainpostHeart;
    @BindView(R.id.tv_mainpost_praiseSum)
    TextView mTvMainpostPraiseSum;
    @BindView(R.id.likeBt_mainpost_star)
    LikeButton mLikeBtMainpostStar;
    @BindView(R.id.tv_mainpost_starSum)
    TextView mTvMainpostStarSum;
    @BindView(R.id.recycle_main_common)
    RecyclerView mRecycleMainCommon;
    @BindView(R.id.sw_refresh_main_com)
    SwipeRefreshLayout mSwRefreshMainCom;
    @BindView(R.id.etv_maincom_addcom)
    EditText mEtvMaincomAddcom;
    @BindView(R.id.bt_maincom_addcom)
    Button mBtMaincomAddcom;

    private mainPost mainPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_post_detail);
        ButterKnife.bind(this);

        setListener();
        initView();
        initData();

    }

    @Override
    public void setAdapter() {
        mAdapter = new MainPostComAdapter(mList, MainPostDetailAc.this);
    }

    @Override
    public void initView() {
        mRecyclerView = mRecycleMainCommon;
        mSwipeRefreshLayout = mSwRefreshMainCom;

        super.initView();

        Intent intent = getIntent();
        mainPost = (mainPost) intent.getSerializableExtra("post");
        Log.d("wer", mainPost.toString());

        mTvContentMainPost.setText(mainPost.getContent());
        mTvMainpostPraiseSum.setText(mainPost.getPraiseSum().toString());
        mTvMainpostStarSum.setText(mainPost.getLikeSum().toString());
        mTvTimeMainPost.setText(mainPost.getCreatedAt());
        mTvUsernameMainPost.setText(mainPost.getAuthor().getNickname());
        mLikeBtMainpostHeart.setFocusable(false);
        mLikeBtMainpostStar.setFocusable(false);

        //设置图片
        Tool.setNineViewPic(mainPost, MainPostDetailAc.this, mNinegridviewMainPost);

        mBtMaincomAddcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String com = mEtvMaincomAddcom.getText().toString();
                upLoadCom(com);
                mEtvMaincomAddcom.setText("");

            }
        });
    }

    public void upLoadCom(final String com) {
        final mainCommon common = new mainCommon();
        common.setAuthor(SingleUser.getUser());
        common.setContent(com);
        common.setPost(mainPost);
        common.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    mList.add(common);
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(MainPostDetailAc.this, "评论成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainPostDetailAc.this, "评论失败", Toast.LENGTH_SHORT).show();
                    Log.d("error", e.toString());
                }
            }
        });
    }

    //如果不重写则会出错，但不知道为什么
    public void setListener() {
        mListener_up = new FindListener<mainCommon>() {
            @Override
            public void done(List<mainCommon> list, BmobException e) {
                if (list.size() > 0 && e == null) {
                    theLast = list.get(list.size() - 1);
                    for (mainCommon t : list) {
                        mList.add(t);
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
        };

        mListener_down = new FindListener<mainCommon>() {
            @Override
            public void done(List<mainCommon> list, BmobException e) {
                if (list.size() > 0 && e == null) {
                    theFirst = list.get(list.size() - 1);
                    list.remove(0);
                    for (mainCommon t : list) {
                        mList.add(t);
                    }
                    mAdapter.notifyDataSetChanged();
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        };

        mListener_init = new FindListener<mainCommon>() {
            @Override
            public void done(List<mainCommon> list, BmobException e) {
                if (e == null) {
                    theFirst = list.get(0);
                    theLast = list.get(list.size() - 1);
                    for (mainCommon t : list) {
                        mList.add(t);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        };
    }
}
