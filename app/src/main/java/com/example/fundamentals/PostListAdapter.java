package com.example.fundamentals;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.constraint.Constraints.TAG;
import static android.view.View.VISIBLE;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder> implements CardView.OnLongClickListener{

    // constants
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final int NOTIFICATION_ID = 0;

    // vars
    private final LayoutInflater mInflater;
    private List<Post> mPosts;
    private int mLayout = 0;
    private Context mContext;
    private Boolean isLiked = false;
    private PostViewModel mViewModel;
    private NotificationManager mNotificationManager;

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
        mViewModel = ViewModelProviders.of((FragmentActivity) context).get(PostViewModel.class);
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

        //postViewHolder.cardView.setOnLongClickListener(this);

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

    @Override
    public boolean onLongClick(View view) {
        Log.d(TAG, "onLongClick: Clicked Long on cardview");
        return true;
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle, tvUsername, tvDescription, tvDateCreated, tvPicturePath;
        private final SquareImageView ivGraphic;
        private final SquareImageViewHeight ivThumbnail;
        private final CardView cardView;
        private final CircleImageView ivStarOutline, ivStar, ivTrash;
        private final RelativeLayout relOne;

        public PostViewHolder(@NonNull final View itemView) {
            super(itemView);
            this.tvTitle = itemView.findViewById(R.id.tvTitle);
            this.tvUsername = itemView.findViewById(R.id.tvUsername);
            this.tvDescription = itemView.findViewById(R.id.tvDescription);
            this.tvDateCreated = itemView.findViewById(R.id.tvDateCreated);
            this.tvPicturePath = itemView.findViewById(R.id.tvPicturePath);
            this.ivGraphic = itemView.findViewById(R.id.ivMain);
            this.ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            this.cardView = itemView.findViewById(R.id.cardView);
            this.ivStarOutline = itemView.findViewById(R.id.ivStarOutline);
            this.ivStar = itemView.findViewById(R.id.ivStarFull);
            this.relOne = itemView.findViewById(R.id.relOne);
            this.ivTrash = itemView.findViewById(R.id.ivTrash);

            this.relOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: Clicked on star: " + isLiked);
                    if (isLiked) {
                        itemView.findViewById(R.id.ivStarFull).setVisibility(View.INVISIBLE);
                        itemView.findViewById(R.id.ivStarOutline).setVisibility(View.VISIBLE);
                        isLiked = false;
                    } else {
                        itemView.findViewById(R.id.ivStarFull).setVisibility(View.VISIBLE);
                        itemView.findViewById(R.id.ivStarOutline).setVisibility(View.INVISIBLE);
                        sendNotification(getAdapterPosition());
                        isLiked = true;
                    }
                }
            });

            this.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Log.d(TAG, "onLongClick: Clicked long click on card view");
                    itemView.findViewById(R.id.ivTrash).setVisibility(View.VISIBLE);
                    return true;
                }
            });

            this.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: Clicked on cardview");
                    itemView.findViewById(R.id.ivTrash).setVisibility(View.INVISIBLE);
                }
            });

            this.ivTrash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view.getVisibility() == VISIBLE) {
                        Toast.makeText(mContext, "Deleting this Post now", Toast.LENGTH_SHORT).show();
                        mViewModel.deletePost(mPosts.get(getAdapterPosition()));
                        mPosts.remove(getAdapterPosition());
                        notifyDataSetChanged();
                    }
                }
            });

        }
    }

    private void sendNotification(int adapterPosition) {
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            Post post = mPosts.get(adapterPosition);
            Bitmap bitmap = BitmapFactory.decodeFile(post.getPicturePath());

            Intent intent = new Intent(mContext, CreatePostActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, NOTIFICATION_ID,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationChannel channel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Post Star Notification",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.setLightColor(Color.GREEN);
            channel.enableVibration(true);
            channel.setDescription("Notifications sent when a post is favorited");
            mNotificationManager.createNotificationChannel(channel);

            NotificationCompat.Builder notification = new NotificationCompat.Builder(mContext, PRIMARY_CHANNEL_ID);
            notification.setContentTitle("New Like!")
                    .setContentText("A Post has just been favorited: " + post.getTitle())
                    .setSmallIcon(R.drawable.ic_star_notify)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setLargeIcon(bitmap)
                    .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(post.getDescription()));
            mNotificationManager.notify(NOTIFICATION_ID, notification.build());
        }
    }
}
