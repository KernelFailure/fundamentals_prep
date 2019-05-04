package com.example.fundamentals;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder> {

    private final LayoutInflater mInflater;
    private List<Post> mPosts;
    private int mLayout = 0;
    private Context mContext;

    public PostListAdapter(Context context, List<Post> posts) {
        mInflater = LayoutInflater.from(context);
        mPosts = posts;
        mContext = context;
    }

    public PostListAdapter(Context context, List<Post> mPosts, int mLayout) {
        this.mInflater = LayoutInflater.from(context);
        this.mPosts = mPosts;
        this.mLayout = mLayout;
        mContext = context;
    }

    @NonNull
    @Override
    public PostListAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.layout_post_list_item_graphic, viewGroup, false);
        if (mLayout == R.layout.layout_post_list_item_graphic) {
            itemView = mInflater.inflate(R.layout.layout_post_list_item_graphic, viewGroup, false);
        } else if (mLayout == R.layout.layout_post_list_item_thumbnail) {
            itemView = mInflater.inflate(R.layout.layout_post_list_item_thumbnail, viewGroup, false);
        } else if (mLayout == R.layout.layout_post_list_item_verbose) {
            itemView = mInflater.inflate(R.layout.layout_post_list_item_verbose, viewGroup, false);
        }
        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostListAdapter.PostViewHolder postViewHolder, int i) {

        if (mPosts != null) {
            Post post = mPosts.get(i);
            if (mLayout == 0 || mLayout == R.layout.layout_post_list_item_graphic) {
                postViewHolder.tvTitle.setText(post.getTitle());
                Glide.with(mContext).load(post.getPicturePath()).into(postViewHolder.ivGraphic);
            } else if (mLayout == R.layout.layout_post_list_item_thumbnail) {
                postViewHolder.tvTitle.setText(post.getTitle());
                postViewHolder.tvUsername.setText(post.getUsername());
                Glide.with(mContext).load(post.getPicturePath()).into(postViewHolder.ivThumbnail);
            } else if (mLayout == R.layout.layout_post_list_item_verbose) {
                postViewHolder.tvTitle.setText(post.getTitle());
                postViewHolder.tvUsername.setText(post.getUsername());
                postViewHolder.tvPicturePath.setText(post.getPicturePath());
                postViewHolder.tvDescription.setText(post.getDescription());
            }
        }

    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle, tvUsername, tvDescription, tvDateCreated, tvPicturePath;
        private final SquareImageView ivGraphic;
        private final SquareImageViewHeight ivThumbnail;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvTitle = itemView.findViewById(R.id.tvTitle);
            this.tvUsername = itemView.findViewById(R.id.tvUsername);
            this.tvDescription = itemView.findViewById(R.id.tvDescription);
            this.tvDateCreated = itemView.findViewById(R.id.tvDateCreated);
            this.tvPicturePath = itemView.findViewById(R.id.tvPicturePath);
            this.ivGraphic = itemView.findViewById(R.id.ivMain);
            this.ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
        }
    }
}
