package com.example.java.algorithm.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.java.algorithm.R;
import com.example.java.algorithm.activity.MainPostEditAc;
import com.example.java.algorithm.javabean.user_type;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * author: DeDao233.
 * time: 2018/4/18.
 */
public class MainPostFragment extends Fragment implements
        SwipeRefreshLayout.OnRefreshListener, TabLayout.OnTabSelectedListener {

    public View mView;
    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.pager)
    ViewPager mPager;
    @BindView(R.id.fab_mainpost)
    FloatingActionButton mFabMainpost;
    Unbinder unbinder;


    private String[] titles = {"全部"};
    private List<String> mStrings = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();
    private List<user_type> mUser_types = new ArrayList<>();
    private FragmentManager mFragmentManager;

    //initialize adapter
    private MyAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_mainpost, container, false);
        unbinder = ButterKnife.bind(this, mView);

        Log.d("wer", "fragment_create");
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initTitle();

        Log.d("wer", "fragment_ac_create");
    }

    public void initView() {

        mFabMainpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainPostEditAc.class);

                startActivity(intent);
            }
        });
        mPager.addOnAdapterChangeListener(new ViewPager.OnAdapterChangeListener() {
            @Override
            public void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter oldAdapter, @Nullable PagerAdapter newAdapter) {

            }
        });
        //重要！！是child
        mFragmentManager = getChildFragmentManager();
        adapter = new MyAdapter(mFragmentManager);
        mPager.setAdapter(adapter);
        mTablayout.addOnTabSelectedListener(this);
        mTablayout.setupWithViewPager(mPager);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTablayout));
    }

    /**
     * 初始化标签类型
     */
    public void initTitle() {

        BmobQuery<user_type> query = new BmobQuery<>();
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
//        progressDialog.show();
        query.findObjects(new FindListener<user_type>() {
            @Override
            public void done(List<user_type> list, BmobException e) {
                if (e == null) {

                    mStrings.clear();

                    for (user_type u : list) {
                        String s = u.getType_sign();
                        if (s.length() > 3) {
                            mUser_types.add(u);
                            mStrings.add(u.getType_name());
                        }

                    }
                    titles = null;
                    titles = new String[mStrings.size()];
                    for (int i = 0; i < mStrings.size(); i++) {
                        titles[i] = mStrings.get(i);
                    }
                    Log.d("wer", "" + titles.length);
                    initView();
                    progressDialog.dismiss();
                    adapter.notifyDataSetChanged();

                } else {
                    Log.d("wer", "" + e.toString());
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("wer", "fragment_destroy");
        unbinder.unbind();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int n = tab.getPosition();

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onRefresh() {

    }

    class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
            // TODO Auto-generated constructor stub

        }

        @Override
        public CharSequence getPageTitle(int position) {
            // TODO Auto-generated method stub

            return titles[position];
        }

        @Override
        public Fragment getItem(int arg0) {
            // TODO Auto-generated method stub
            Bundle bundle = new Bundle();
            bundle.putSerializable("title", mUser_types.get(arg0));

            if (!mUser_types.get(arg0).getType_name().equals(titles[arg0])) {
                try {
                    throw new Exception("the user_type is not equal with title");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            MainPostSubFm fragment = new MainPostSubFm("" + arg0, titles[arg0]);
            fragment.setArguments(bundle);
            Log.d("wer", "" + titles[arg0]);
            Log.d("wer", "fragment_getItem_" + arg0);
            mFragments.add(fragment);
            return fragment;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return titles.length;
        }
    }
}
