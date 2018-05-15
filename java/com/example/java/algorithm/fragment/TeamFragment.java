package com.example.java.algorithm.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.java.algorithm.R;
import com.example.java.algorithm.adapter.TeamAdapter;
import com.example.java.algorithm.javabean.team;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class TeamFragment extends ItemFragment<TeamAdapter,team> {

    public View mView;
    @BindView(R.id.team_list)
    RecyclerView mTeamList;
    @BindView(R.id.sw_refresh_team)
    SwipeRefreshLayout mSwRefreshTeam;
    Unbinder unbinder;

    private List<team> mTeams = new ArrayList<>();

    public TeamFragment() {
        mContext = getContext();
        mAdapter = new TeamAdapter(mTeams, mContext);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_item_list, container, false);
        unbinder = ButterKnife.bind(this, mView);

        setListener();
        initView();
        initData();

        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setListener() {

        mListener_up = new FindListener<team>() {
            @Override
            public void done(List<team> list, BmobException e) {
                if (list.size() > 0 && e==null) {
                    theLast= list.get(list.size() - 1);
                    for (team t : list) {
                        mTeams.add(t);
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
        };

        mListener_down=new FindListener<team>() {
            @Override
            public void done(List<team> list, BmobException e) {
                if (list.size() > 0 && e==null) {
                    theFirst = list.get(list.size() - 1);
                    list.remove(0);
                    for (team t : list) {
                        mTeams.add(t);
                    }
                    mAdapter.notifyDataSetChanged();
                }
                mSwRefreshTeam.setRefreshing(false);
            }
        };

        mListener_init=new FindListener<team>() {
            @Override
            public void done(List<team> list, BmobException e) {
                if (e == null) {
                    theFirst = list.get(0);
                    theLast = list.get(list.size() - 1);
                    for (team t : list) {
                        mTeams.add(t);
                        Log.d("fragmentId", "" + t.getUsername().getNickname());
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        };
    }

    public void initView() {

        mSwRefreshTeam.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                downRefresh();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mTeamList.setLayoutManager(layoutManager);
        mTeamList.setAdapter(mAdapter);
        mTeamList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
//                    Toast.makeText(mContext, "到底部", Toast.LENGTH_SHORT).show();
                    upRefresh();
                }
            }
        });
    }
}
