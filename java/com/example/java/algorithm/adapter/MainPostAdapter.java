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
import com.example.java.algorithm.activity.MainPostDetailAc;
import com.example.java.algorithm.javabean._User;
import com.example.java.algorithm.javabean.mainPost;
import com.example.java.algorithm.tool.Tool;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.lzy.ninegrid.NineGridView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * author: DeDao233.
 * time: 2018/4/9.
 */

public class MainPostAdapter extends RecyclerView.Adapter<MainPostAdapter.ViewHolder> {

    private List<mainPost> mList;
    public OnItemClickListener mClickListener;
    private Context mContext;

    //test pictures' path
//    private final String s1 = "/storage/emulated/0/Pictures/二柄/1523492936577.jpeg";
//    private final String s2 = "/storage/emulated/0/Pictures/二柄/1523492929888.jpeg";

    public MainPostAdapter(List<mainPost> list, Context context) {
        mList = list;
        mContext = context;
        Log.d("wer", mContext.toString());
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_mainpost,
                parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final mainPost post = mList.get(position);
        holder.mImageUserPicMainPost.setImageResource(R.mipmap.ic_launcher_round);
        holder.mTvContentMainPost.setText(post.getContent());
        holder.mTvUsernameMainPost.setText(post.getAuthor().getNickname());
        holder.mTvTimeMainPost.setText(post.getUpdatedAt());
        holder.mTvMainpostPraiseSum.setText(post.getPraiseSum().toString());
        holder.mTvMainpostStarSum.setText(post.getLikeSum().toString());
        holder.mTvContentMainPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击内容进入详情页面
                Intent intent = new Intent(mContext, MainPostDetailAc.class);
                intent.putExtra("post", post);
                mContext.startActivity(intent);

            }
        });

        //设置点赞点击事件
        holder.mLikeBtMainpostHeart.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                post.increment("praiseSum");
                post.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(mContext, "喜欢", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                holder.mTvMainpostPraiseSum.setText(String.valueOf(post.getPraiseSum()+1));
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                post.increment("praiseSum",-1);
                post.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(mContext, "没兴趣...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                holder.mTvMainpostPraiseSum.setText(String.valueOf(post.getPraiseSum()));
            }
        });

        //设置收藏点击事件
        holder.mLikeBtMainpostStar.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                post.increment("likeSum");
                post.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(mContext, "收藏", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                holder.mTvMainpostStarSum.setText(String.valueOf(post.getLikeSum() + 1));

                //添加多对多关系：该用户收藏了帖子
                BmobRelation bmobRelation = new BmobRelation();
                bmobRelation.add(post);
                _User user = BmobUser.getCurrentUser(_User.class);
                user.setLikes_main(bmobRelation);
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
                post.increment("likeSum", -1);
                post.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(mContext, "取消收藏", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                holder.mTvMainpostStarSum.setText(String.valueOf(post.getLikeSum()));
                //删除多对多关系：用户取消了收藏
                _User user = BmobUser.getCurrentUser(_User.class);
                BmobRelation relation = new BmobRelation();
                relation.remove(post);
                user.setLikes_main(relation);
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
        BmobQuery<mainPost> query = new BmobQuery<>();
        query.addWhereRelatedTo("likes_main",
                new BmobPointer(BmobUser.getCurrentUser(_User.class)));
        query.findObjects(new FindListener<mainPost>() {
            @Override
            public void done(List<mainPost> list, BmobException e) {
                if (e == null) {
                    for (mainPost post1 : list) {
                        //查询收藏的列表里面是否有当前的帖子
                        if (post1.getObjectId().equals(post.getObjectId())) {
                            holder.mLikeBtMainpostStar.setLiked(true);
                            Log.d("wer", "star true");
                            break;
                        }
                    }
                } else {
                    Toast.makeText(mContext, "error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //设置图片
        Tool.setNineViewPic(post,mContext,holder.mNinegridviewMainPost);

//        ArrayList<ImageInfo> imageInfo = new ArrayList<>();
//        List<String> imageDetails = new ArrayList<>();
//
//        for (String s : post.getPicUrl()) {
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
//        holder.mNinegridviewMainPost.setAdapter(new NineGridViewClickAdapter(mContext, imageInfo));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_userPic_mainPost)
        ImageView mImageUserPicMainPost;
        @BindView(R.id.tv_username_mainPost)
        TextView mTvUsernameMainPost;
        @BindView(R.id.tv_time_mainPost)
        TextView mTvTimeMainPost;
        @BindView(R.id.tv_content_mainPost)
        TextView mTvContentMainPost;
        @BindView(R.id.ninegridview_mainPost)
        NineGridView mNinegridviewMainPost;
        @BindView(R.id.likeBt_mainpost_heart)
        LikeButton mLikeBtMainpostHeart;
        @BindView(R.id.tv_mainpost_praiseSum)
        TextView mTvMainpostPraiseSum;
        @BindView(R.id.likeBt_mainpost_star)
        LikeButton mLikeBtMainpostStar;
        @BindView(R.id.tv_mainpost_starSum)
        TextView mTvMainpostStarSum;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
