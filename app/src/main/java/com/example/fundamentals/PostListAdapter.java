package com.example.fundamentals;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder> {

    private final LayoutInflater mInflater;
    private List<Post> mPosts;
    private int mLayout = 0;

    public PostListAdapter(Context context, List<Post> posts) {
        mInflater = LayoutInflater.from(context);
        mPosts = posts;
    }

    public PostListAdapter(Context context, List<Post> mPosts, int mLayout) {
        this.mInflater = LayoutInflater.from(context);
        this.mPosts = mPosts;
        this.mLayout = mLayout;
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
            postViewHolder.tvTitle.setText("Title: " + post.getTitle());
            postViewHolder.tvUsername.setText("Username: " + post.getUsername());
            postViewHolder.tvDescription.setText(post.getDescription());
            postViewHolder.tvDateCreated.setText(post.getDateCreated());
            postViewHolder.tvPicturePath.setText(post.getPicturePath());
        }

    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle, tvUsername, tvDescription, tvDateCreated, tvPicturePath;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvTitle = itemView.findViewById(R.id.tvTitle);
            this.tvUsername = itemView.findViewById(R.id.tvUsername);
            this.tvDescription = itemView.findViewById(R.id.tvDescription);
            this.tvDateCreated = itemView.findViewById(R.id.tvDateCreated);
            this.tvPicturePath = itemView.findViewById(R.id.tvPicturePath);
        }
    }
}
