package com.example.java.algorithm.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.ninegrid.NineGridView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * author: DeDao233.
 * time: 2018/5/8.
 */
public abstract class ItemBaseAc<T_adapter extends RecyclerView.Adapter,
        T extends BmobObject> extends BaseActivity {

    public T_adapter mAdapter;
    public List<T> mList = new ArrayList<>();
    public SwipeRefreshLayout mSwipeRefreshLayout;
    public RecyclerView mRecyclerView;

    //about bmob listeners' setting
    public T theFirst;
    public T theLast;
    public FindListener<T> mListener_up,mListener_down,mListener_init;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NineGridView.setImageLoader(new GlideImageLoader());

        setAdapter();
    }

    public abstract void setAdapter();

    public  void setListener(){
        mListener_up = new FindListener<T>() {
            @Override
            public void done(List<T> list, BmobException e) {
                if (list.size() > 0 && e == null) {
                    theLast = list.get(list.size() - 1);
                    for (T t : list) {
                        mList.add(t);
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
        };

        mListener_down = new FindListener<T>() {
            @Override
            public void done(List<T> list, BmobException e) {
                if (list.size() > 0 && e == null) {
                    theFirst = list.get(list.size() - 1);
                    list.remove(0);
                    for (T t : list) {
                        mList.add(t);
                    }
                    mAdapter.notifyDataSetChanged();
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        };

        mListener_init = new FindListener<T>() {
            @Override
            public void done(List<T> list, BmobException e) {
                if (e == null) {
                    theFirst = list.get(0);
                    theLast = list.get(list.size() - 1);
                    for (T t : list) {
                        mList.add(t);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        };
    }

    public void initView(){
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                downRefresh();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
                    upRefresh();
                }
            }
        });
    }

    public void upRefresh() {
        upRefreshQuery(theLast,mListener_up);
    }

    public void downRefresh() {
        downRefreshQuery(theFirst, mListener_down);
    }

    public void initData() {
        initDataQuery(mListener_init);
    }

    public void initDataQuery(FindListener findListener) {
        BmobQuery<T> query = new BmobQuery<>();
        query.setLimit(5);
        query.order("-createdAt");
        query.include("author");
        query.findObjects(findListener);
    }

    public void upRefreshQuery(T last, FindListener findListener) {
        BmobQuery<T> query = new BmobQuery<>();
        String start = last.getCreatedAt();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(start);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        query.addWhereLessThan("createdAt", new BmobDate(date));
        query.order("-createdAt");
        query.include("author");
        query.setLimit(3);
        query.findObjects(findListener);
    }

    public void downRefreshQuery(T first, FindListener findListener) {
        BmobQuery<T> query = new BmobQuery<>();

        //查询时间大于当前页面第一个文章的创建时间的
        String start = first.getCreatedAt();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(start);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        query.addWhereGreaterThan("createdAt", new BmobDate(date));
        query.order("createdAt");
        query.include("author");
        query.findObjects(findListener);
    }

    public class GlideImageLoader implements NineGridView.ImageLoader {

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
