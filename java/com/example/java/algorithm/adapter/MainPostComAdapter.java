package com.example.java.algorithm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.java.algorithm.R;
import com.example.java.algorithm.javabean.mainCommon;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: DeDao233.
 * time: 2018/5/8.
 */
public class MainPostComAdapter extends RecyclerView.Adapter<MainPostComAdapter.ViewHolder> {

    private List<mainCommon> mCommons;
    private Context mContext;

    public MainPostComAdapter(List<mainCommon> commons, Context context) {
        mContext = context;
        mCommons = commons;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_common,
                parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mainCommon common = mCommons.get(position);
        holder.mTvMainComContent.setText(common.getContent());
        holder.mTvMainCommonUsername.setText(common.getAuthor().getNickname());

    }

    @Override
    public int getItemCount() {
        return mCommons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_main_common_userPic)
        ImageView mImageMainCommonUserPic;
        @BindView(R.id.tv_main_common_username)
        TextView mTvMainCommonUsername;
        @BindView(R.id.tv_main_com_content)
        TextView mTvMainComContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
