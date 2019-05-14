package com.example.fundamentals;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import java.util.ArrayList;
import java.util.List;

import static com.example.fundamentals.DummyData.descriptions;
import static com.example.fundamentals.DummyData.picturePaths;
import static com.example.fundamentals.DummyData.titles;
import static com.example.fundamentals.DummyData.usernames;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    // Constants
    private final static String LAYOUT_RESOURCE_INT = "layout_resource_int";
    private final static String LAYOUT_KEY = "layout_key";

    // vars
    PostListAdapter mAdapter;
    List<Post> mPosts;
    Context mContext;
    RecyclerView.LayoutManager mLayoutManager;
    int itemLayout = 0;
    SharedPreferences mPreferences;
    String sharedPrefFile = "com.example.fundamentals";
    PostViewModel mViewModel;

    // widgets
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mPosts = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        itemLayout = mPreferences.getInt(LAYOUT_KEY, R.layout.layout_post_list_item_graphic);

        mViewModel = ViewModelProviders.of(this).get(PostViewModel.class);

        //mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.grid_column_count));

        if (savedInstanceState == null) {
            Log.d(TAG, "onCreate: Saved Instance was null");
            mAdapter = new PostListAdapter(this, mPosts, itemLayout);

        } else {
            Log.d(TAG, "onCreate: Something was in saved instance");
            itemLayout = savedInstanceState.getInt(LAYOUT_RESOURCE_INT);
            mAdapter = new PostListAdapter(mContext, mPosts, itemLayout);
        }

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        mViewModel.getAllPosts().observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(@Nullable List<Post> posts) {
                Log.d(TAG, "onChanged: Observed change");
                if (posts != null) {
                    mPosts.addAll(posts);
                    mAdapter.notifyDataSetChanged();
                    Log.d(TAG, "onChanged: New Post list size: " + mPosts.size());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newPost:
                Log.d(TAG, "onOptionsItemSelected: Clicked new post");
                recyclerView.setAdapter(null);
                recyclerView.setLayoutManager(null);
                Intent intent = new Intent(mContext, CreatePostActivity.class);
                startActivity(intent);
                break;
            case R.id.graphic:
                Log.d(TAG, "onOptionsItemSelected: Clicked on graphic");
                mAdapter = new PostListAdapter(mContext, mPosts, R.layout.layout_post_list_item_graphic);
//                recyclerView.setLayoutManager(null);
//                recyclerView.setAdapter(null);
                recyclerView.setAdapter(mAdapter);
                recyclerView.setLayoutManager(mLayoutManager);
                itemLayout = R.layout.layout_post_list_item_graphic;
                break;
            case R.id.thumbnail:
                Log.d(TAG, "onOptionsItemSelected: Clicked on thumbnail");
                mAdapter = new PostListAdapter(mContext, mPosts, R.layout.layout_post_list_item_thumbnail);
//                recyclerView.setLayoutManager(null);
//                recyclerView.setAdapter(null);
                recyclerView.setAdapter(mAdapter);
                recyclerView.setLayoutManager(mLayoutManager);
                itemLayout = R.layout.layout_post_list_item_thumbnail;
                break;
            case R.id.verbose:
                Log.d(TAG, "onOptionsItemSelected: Clicked on verbose");
                mAdapter = new PostListAdapter(mContext, mPosts, R.layout.layout_post_list_item_verbose);
//                recyclerView.setLayoutManager(null);
//                recyclerView.setAdapter(null);
                recyclerView.setAdapter(mAdapter);
                recyclerView.setLayoutManager(mLayoutManager);
                itemLayout = R.layout.layout_post_list_item_verbose;
                break;
            default:
                Log.d(TAG, "onOptionsItemSelected: Nothing clicked in spinner option menu");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (itemLayout != 0) {
            Log.d(TAG, "onSaveInstanceState: Item Layout wasn't 0");
            outState.putInt(LAYOUT_RESOURCE_INT, itemLayout);
        } else {
            Log.d(TAG, "onSaveInstanceState: Item Layout: " + itemLayout);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Just resumed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Just Paused");
        if (itemLayout != 0) {
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putInt(LAYOUT_KEY, itemLayout);
            editor.apply();
        }
    }
}
