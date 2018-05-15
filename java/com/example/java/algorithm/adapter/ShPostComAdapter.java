package com.example.java.algorithm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.java.algorithm.R;
import com.example.java.algorithm.javabean.shCommon;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: DeDao233.
 * time: 2018/5/10.
 */
public class ShPostComAdapter extends RecyclerView.Adapter<ShPostComAdapter.ViewHolder> {

    private List<shCommon> mCommons;
    private Context mContext;

    public ShPostComAdapter(List<shCommon> commons, Context context) {
        mCommons = commons;
        mContext = context;
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
        shCommon shCommon = mCommons.get(position);
        holder.mTvMainComContent.setText(shCommon.getContent());
        holder.mTvMainCommonUsername.setText(shCommon.getAuthor().getNickname());

   }

    @Override
    public int getItemCount() {
        return mCommons.size();
    }

    static

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
