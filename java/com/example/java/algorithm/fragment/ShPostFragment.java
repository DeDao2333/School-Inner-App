package com.example.java.algorithm.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.java.algorithm.R;
import com.example.java.algorithm.adapter.ShPostAdapter;
import com.example.java.algorithm.javabean.shPost;
import com.lzy.ninegrid.NineGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * author: DeDao233.
 * time: 2018/4/7.
 */

public class ShPostFragment extends ItemFragment<ShPostAdapter, shPost> {

    public View mView;
    @BindView(R.id.recycle_shPost_list)
    RecyclerView mRecycleShPostList;
    @BindView(R.id.sw_refresh_shpost)
    SwipeRefreshLayout mSwRefreshShpost;
    Unbinder unbinder;

    private List<shPost> mShPosts = new ArrayList<>();

    public ShPostFragment() {
        mContext = getApplicationContext();
        mAdapter = new ShPostAdapter(mShPosts, mContext);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_shpost_list, container, false);
        NineGridView.setImageLoader(new GlideImageLoader());
        unbinder = ButterKnife.bind(this, mView);

        setListener();
        initView();
        initData();

        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //set callback about initData,upRefresh,downRefresh
    public void setListener() {

        mListener_up = new FindListener<shPost>() {
            @Override
            public void done(List<shPost> list, BmobException e) {
                if (list.size() > 0 && e == null) {
                    theLast = list.get(list.size() - 1);
                    for (shPost t : list) {
                        mShPosts.add(t);
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
        };

        mListener_down = new FindListener<shPost>() {
            @Override
            public void done(List<shPost> list, BmobException e) {
                if (list.size() > 0 && e == null) {
                    theFirst = list.get(list.size() - 1);
                    list.remove(0);
                    for (shPost t : list) {
                        mShPosts.add(t);
                    }
                    mAdapter.notifyDataSetChanged();
                }
                mSwRefreshShpost.setRefreshing(false);
            }
        };

        mListener_init = new FindListener<shPost>() {
            @Override
            public void done(List<shPost> list, BmobException e) {
                if (e == null) {
                    theFirst = list.get(0);
                    theLast = list.get(list.size() - 1);
                    for (shPost t : list) {
                        mShPosts.add(t);
                        Log.d("fragmentId", "" + t.getAuthor().getNickname());
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        };
    }

    public void initView() {
        mSwRefreshShpost.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                downRefresh();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecycleShPostList.setLayoutManager(layoutManager);
        mRecycleShPostList.setAdapter(mAdapter);
        mRecycleShPostList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
                    upRefresh();
                }
            }
        });
    }

//    private class GlideImageLoader implements NineGridView.ImageLoader {
//
//        @Override
//        public void onDisplayImage(Context context, ImageView imageView, String url) {
//            Glide.with(context)
//                    .load(url)//
//                    .into(imageView);
//        }
//
//        @Override
//        public Bitmap getCacheImage(String url) {
//            return null;
//        }
//    }
}
