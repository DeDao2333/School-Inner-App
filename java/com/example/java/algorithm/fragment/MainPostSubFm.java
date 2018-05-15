package com.example.java.algorithm.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.java.algorithm.R;
import com.example.java.algorithm.adapter.MainPostAdapter;
import com.example.java.algorithm.javabean.mainPost;
import com.example.java.algorithm.javabean.user_type;
import com.lzy.ninegrid.NineGridView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class MainPostSubFm extends ItemFragment<MainPostAdapter, mainPost> {

    @BindView(R.id.recycle_mainPost_list)
    RecyclerView mRecycleMainPostList;
    @BindView(R.id.sw_refresh_mainPost)
    SwipeRefreshLayout mSwRefreshMainPost;
    Unbinder unbinder;

    private String value;
    private String loadType;
    private user_type mUser_type;


    private List<mainPost> mMainPosts = new ArrayList<>();

    public MainPostSubFm(String value1,String loadType1) {
        // Required empty public constructor
        mContext = getApplicationContext();
        Log.d("wer", mContext.getPackageName());
        mAdapter = new MainPostAdapter(mMainPosts, mContext);
        value = value1;
        loadType = loadType1;
        Log.d("wer", "new_f_sub_"+value);
    }

    @Override
    public void setListener() {
        mListener_init=new FindListener<mainPost>() {
            @Override
            public void done(List<mainPost> list, BmobException e) {
                if (e == null) {
                    theFirst = list.get(0);
                    theLast = list.get(list.size() - 1);
                    mMainPosts.clear();
                    for (mainPost t : list) {
                        mMainPosts.add(t);
                    }

                }
                mAdapter.notifyDataSetChanged();
            }
        };

        mListener_down=new FindListener<mainPost>() {
            @Override
            public void done(List<mainPost> list, BmobException e) {
                if (list.size() > 0 && e == null) {
                    theFirst = list.get(list.size() - 1);
                    list.remove(0);
                    for (mainPost t : list) {
                            mMainPosts.add(t);
                    }
                    mAdapter.notifyDataSetChanged();
                }
                mSwRefreshMainPost.setRefreshing(false);
            }
        };

        mListener_up=new FindListener<mainPost>() {
            @Override
            public void done(List<mainPost> list, BmobException e) {
                if (list.size() > 0 && e == null) {
                    theLast = list.get(list.size() - 1);
                    for (mainPost t : list) {
                            mMainPosts.add(t);
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
        };
    }

    @Override
    public void initDataQuery(FindListener findListener) {
        BmobQuery<mainPost> query = new BmobQuery<>();
        query.addWhereEqualTo("mUser_type", mUser_type);
        Log.d("wer", mUser_type.getObjectId());
        query.setLimit(5);
        query.order("-createdAt");
        query.include("author");
        query.findObjects(findListener);
    }

    @Override
    public void upRefreshQuery(mainPost last, FindListener findListener) {
        BmobQuery<mainPost> query = new BmobQuery<>();
        String start = last.getCreatedAt();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(start);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        query.addWhereLessThan("createdAt", new BmobDate(date));
        query.addWhereEqualTo("mUser_type", mUser_type);
        query.order("-createdAt");
        query.include("author");
        query.setLimit(3);
        query.findObjects(findListener);
    }

    @Override
    public void downRefreshQuery(mainPost first, FindListener findListener) {
        BmobQuery<mainPost> query = new BmobQuery<>();

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
        query.addWhereEqualTo("mUser_type", mUser_type);
        query.order("createdAt");
        //内部查询
        query.include("author");
        query.findObjects(findListener);
    }

    @Override
    public void initView() {
        mSwRefreshMainPost.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                downRefresh();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecycleMainPostList.setLayoutManager(layoutManager);
        mRecycleMainPostList.setAdapter(mAdapter);
        mRecycleMainPostList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
                    upRefresh();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_post_sub_fm, container, false);
        unbinder = ButterKnife.bind(this, view);

        NineGridView.setImageLoader(new GlideImageLoader());
        Bundle bundle = getArguments();
        mUser_type = (user_type) bundle.getSerializable("title");

        Log.d("wer", "f_sub_oncreate_"+mUser_type.getType_name());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setListener();
        initView();
        initData();
        Log.d("wer", "f_sub_on_ac_"+value);
    }

    @Override
    public void onDetach() {
        Log.d("wer", "f_sub_detach_"+value );
        super.onDetach();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        Log.d("wer", "f_sub_destroy_"+value );
    }
}
