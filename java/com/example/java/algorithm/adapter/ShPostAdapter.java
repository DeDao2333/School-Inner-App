package com.example.java.algorithm.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.java.algorithm.R;
import com.example.java.algorithm.Test.TestData;
import com.example.java.algorithm.activity.ShPostDetailAc;
import com.example.java.algorithm.javabean._User;
import com.example.java.algorithm.javabean.shPost;
import com.example.java.algorithm.tool.Tool;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.lzy.ninegrid.NineGridView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * author: DeDao233.
 * time: 2018/4/7.
 */

public class ShPostAdapter extends RecyclerView.Adapter<ShPostAdapter.ViewHolder> {

    private List<shPost> mShPosts;
    public OnItemClickListener mClickListener;
    private Context mContext;
    private final String s1 = "/storage/emulated/0/Pictures/二柄/1523492936577.jpeg";
    private final String s2 = "/storage/emulated/0/Pictures/二柄/1523492929888.jpeg";

    public ShPostAdapter(List<shPost> shPosts, Context context) {
        mContext =context;
        mShPosts = shPosts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_shpost,
                parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final shPost shPost = mShPosts.get(position);
        holder.mImageUserPicShpost.setImageResource(R.mipmap.ic_launcher_round);
        holder.mTvUsernameShpost.setText(shPost.getAuthor().getNickname());
        holder.mTvContentShpost.setText(shPost.getContent());
        holder.mTvPriceShpost.setText(shPost.getPrice().toString());
        holder.mTvShpostPraiseSum.setText(shPost.getPraiseSum().toString());
        holder.mTvShpostStarSum.setText(shPost.getLikeSum().toString());
        holder.mTvContentShpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击内容进入详情页面
                Intent intent = new Intent(mContext, ShPostDetailAc.class);
                intent.putExtra("post", shPost);
                mContext.startActivity(intent);
            }
        });

        //设置点赞点击事件
        holder.mLikeBtShpostHeart.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                shPost.increment("praiseSum");
                shPost.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(mContext, "喜欢", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                holder.mTvShpostPraiseSum.setText(String.valueOf(shPost.getPraiseSum()+1));
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                shPost.increment("praiseSum",-1);
                shPost.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(mContext, "没兴趣...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                holder.mTvShpostPraiseSum.setText(String.valueOf(shPost.getPraiseSum()));
            }
        });

        //设置收藏点击事件
        holder.mLikeBtShpostStar.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                shPost.increment("likeSum");
                shPost.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(mContext, "收藏", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                holder.mTvShpostStarSum.setText(String.valueOf(shPost.getLikeSum() + 1));

                //添加多对多关系：该用户收藏了帖子
                BmobRelation bmobRelation = new BmobRelation();
                bmobRelation.add(shPost);
                _User user = BmobUser.getCurrentUser(_User.class);
                user.setLikes_sh(bmobRelation);
                user.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(mContext, "收藏成功~", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "收藏失败...", Toast.LENGTH_SHORT).show();
                            Log.d("wer", e.toString());
                        }
                    }
                });
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                shPost.increment("likeSum", -1);
                shPost.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(mContext, "取消收藏", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                holder.mTvShpostStarSum.setText(String.valueOf(shPost.getLikeSum()));
                //删除多对多关系：用户取消了收藏
                _User user = BmobUser.getCurrentUser(_User.class);
                BmobRelation relation = new BmobRelation();
                relation.remove(shPost);
                user.setLikes_sh(relation);
                user.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(mContext, "取消成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(mContext, "error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //设置点赞收藏的初始状态:查询对多对关系
        BmobQuery<shPost> query = new BmobQuery<>();
        query.addWhereRelatedTo("likes_sh", new BmobPointer(TestData.getTestUser()));
        query.findObjects(new FindListener<shPost>() {
            @Override
            public void done(List<shPost> list, BmobException e) {
                if (e == null) {
                    for (shPost shPost1 : list) {
                        //查询收藏的列表里面是否有当前的帖子
                        if (shPost1.getObjectId().equals(shPost.getObjectId())) {
                            holder.mLikeBtShpostStar.setLiked(true);
                            Log.d("wer", "star true");
                            break;
                        }
                    }
                } else {
                    Toast.makeText(mContext, "error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //获取图片
        Tool.setNineViewPic(shPost, mContext, holder.mNinegridviewShpost);

//        ArrayList<ImageInfo> imageInfo = new ArrayList<>();
//        List<String> imageDetails = new ArrayList<>();
//
//        for (String s : shPost.getPics()) {
//            imageDetails.add(s);
//        }
//
//        if (imageDetails != null) {
//            for (String imageDetail : imageDetails) {
//                ImageInfo info = new ImageInfo();
//                info.setThumbnailUrl(imageDetail);
//                info.setBigImageUrl(imageDetail);
//                imageInfo.add(info);
//            }
//        }
//        holder.mNinegridviewShpost.setAdapter(new NineGridViewClickAdapter(mContext, imageInfo));
    }

    @Override
    public int getItemCount() {
        return mShPosts.size();
    }

    @OnClick(R.id.image_userPic_shpost)
    public void onMImageUserPicShpostClicked() {
        mClickListener.OnItemClick(R.id.image_userPic_shpost);
    }

    @OnClick(R.id.tv_username_shpost)
    public void onMTvUsernameShpostClicked() {
        mClickListener.OnItemClick(R.id.tv_username_shpost);
    }

    @OnClick(R.id.tv_content_shpost)
    public void onMTvContentShpostClicked() {
        mClickListener.OnItemClick(R.id.tv_content_shpost);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
