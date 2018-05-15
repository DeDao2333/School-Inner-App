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
import com.example.java.algorithm.adapter.ShPostComAdapter;
import com.example.java.algorithm.javabean.shCommon;
import com.example.java.algorithm.javabean.shPost;
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

public class ShPostDetailAc extends ItemBaseAc<ShPostComAdapter,shCommon> {

    @BindView(R.id.image_userPic_shpost)
    ImageView mImageUserPicShpost;
    @BindView(R.id.tv_username_shpost)
    TextView mTvUsernameShpost;
    @BindView(R.id.tv_price_shpost)
    TextView mTvPriceShpost;
    @BindView(R.id.tv_content_shpost)
    TextView mTvContentShpost;
    @BindView(R.id.ninegridview_shpost)
    NineGridView mNinegridviewShpost;
    @BindView(R.id.likeBt_shpost_heart)
    LikeButton mLikeBtShpostHeart;
    @BindView(R.id.tv_shpost_praiseSum)
    TextView mTvShpostPraiseSum;
    @BindView(R.id.likeBt_shpost_star)
    LikeButton mLikeBtShpostStar;
    @BindView(R.id.tv_shpost_starSum)
    TextView mTvShpostStarSum;
    @BindView(R.id.recycle_sh_common)
    RecyclerView mRecycleShCommon;
    @BindView(R.id.sw_refresh_sh_com)
    SwipeRefreshLayout mSwRefreshShCom;
    @BindView(R.id.etv_shcom_addcom)
    EditText mEtvShcomAddcom;
    @BindView(R.id.bt_shcom_addcom)
    Button mBtShcomAddcom;

    private shPost mShPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sh_post_detail);
        ButterKnife.bind(this);

        setListener();
        initView();
        initData();
    }

    @Override
    public void initView() {
        mRecyclerView = mRecycleShCommon;
        mSwipeRefreshLayout = mSwRefreshShCom;

        super.initView();

        Intent intent = getIntent();
        mShPost= (shPost) intent.getSerializableExtra("post");

        mTvContentShpost.setText(mShPost.getContent());
        mTvShpostPraiseSum.setText(mShPost.getPraiseSum().toString());
        mTvShpostStarSum.setText(mShPost.getLikeSum().toString());
        mTvUsernameShpost.setText(mShPost.getAuthor().getNickname());
        mLikeBtShpostHeart.setFocusable(false);
        mLikeBtShpostStar.setFocusable(false);

        //设置图片
        Tool.setNineViewPic(mShPost, ShPostDetailAc.this, mNinegridviewShpost);

        mBtShcomAddcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String com = mEtvShcomAddcom.getText().toString();
                upLoadCom(com);
                mEtvShcomAddcom.setText("");

            }
        });
    }

    @Override
    public void setAdapter() {
        mAdapter = new ShPostComAdapter(mList,ShPostDetailAc.this);
    }

    //如果不重写则会出错，但不知道为什么
    public void setListener() {
        mListener_up = new FindListener<shCommon>() {
            @Override
            public void done(List<shCommon> list, BmobException e) {
                if (list.size() > 0 && e == null) {
                    theLast = list.get(list.size() - 1);
                    for (shCommon t : list) {
                        mList.add(t);
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
        };

        mListener_down = new FindListener<shCommon>() {
            @Override
            public void done(List<shCommon> list, BmobException e) {
                if (list.size() > 0 && e == null) {
                    theFirst = list.get(list.size() - 1);
                    list.remove(0);
                    for (shCommon t : list) {
                        mList.add(t);
                    }
                    mAdapter.notifyDataSetChanged();
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        };

        mListener_init = new FindListener<shCommon>() {
            @Override
            public void done(List<shCommon> list, BmobException e) {
                if (e == null) {
                    theFirst = list.get(0);
                    theLast = list.get(list.size() - 1);
                    for (shCommon t : list) {
                        mList.add(t);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        };
    }

    public void upLoadCom(final String com) {
        final shCommon common = new shCommon();
        common.setAuthor(SingleUser.getUser());
        common.setContent(com);
        common.setPost(mShPost);
        common.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    mList.add(common);
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(ShPostDetailAc.this, "评论成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ShPostDetailAc.this, "评论失败", Toast.LENGTH_SHORT).show();
                    Log.d("error", e.toString());
                }
            }
        });
    }
}
