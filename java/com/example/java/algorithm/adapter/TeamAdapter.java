package com.example.java.algorithm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.java.algorithm.R;
import com.example.java.algorithm.javabean.team;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {

    private List<team> mTeams;
    private Context mContext;

    public TeamAdapter(List<team> items, Context context) {
        mContext = context;
        mTeams = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_team, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        team mTeam = mTeams.get(position);
        holder.mTvTeamTitle.setText(mTeam.getTitle());
        holder.mTvTeamPublicTime.setText(mTeam.getUpdatedAt());
        holder.mTvTeamPlace.setText(mTeam.getPlace());
        holder.mTvTeamContent.setText(mTeam.getContent());
        holder.mTvTeamCurPeo.setText(mTeam.getCur_peo().toString());
        holder.mTvTeamMaxPeo.setText(mTeam.getMax_peo().toString());
        holder.mTvTeamStartTime.setText(mTeam.getTime_start().getDate());
        holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext, TeamSubAc.class);
//                mContext.startActivity(intent);
            }
        });
        final Button button = holder.mBtTeamApply;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button.getText().equals("申请")) {
                    button.setText("已申请");
                } else {
                    button.setText("申请");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTeams.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_team_title)
        TextView mTvTeamTitle;
        @BindView(R.id.tv_team_public_time)
        TextView mTvTeamPublicTime;
        @BindView(R.id.tv_team_content)
        TextView mTvTeamContent;
        @BindView(R.id.tv_team_place)
        TextView mTvTeamPlace;
        @BindView(R.id.tv_team_curPeo)
        TextView mTvTeamCurPeo;
        @BindView(R.id.tv_team_maxPeo)
        TextView mTvTeamMaxPeo;
        @BindView(R.id.tv_team_startTime)
        TextView mTvTeamStartTime;
        @BindView(R.id.bt_team_apply)
        Button mBtTeamApply;
        @BindView(R.id.relativelayout_team_title_container)
        RelativeLayout mRelativeLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
