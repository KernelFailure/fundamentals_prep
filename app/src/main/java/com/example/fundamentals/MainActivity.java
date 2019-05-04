package com.example.fundamentals;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    String[] titles = {"Batman", "Superman", "Wonder Woman", "The Flash", "Aquaman"};
    String[] usernames = {"Bruce", "Clark", "Diana", "Barry", "Arthuer"};

    // vars
    PostListAdapter mAdapter;
    List<Post> mPosts;
    Context mContext;
    RecyclerView.LayoutManager mLayoutManager;

    // widgets
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mPosts = new ArrayList<>();
        for (int i = 0; i <= titles.length - 1; i++) {
            Post post = new Post(titles[i]);
            post.setUsername(usernames[i]);
            mPosts.add(post);
        }
        mAdapter = new PostListAdapter(this, mPosts);
        mLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
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
            case R.id.graphic:
                Log.d(TAG, "onOptionsItemSelected: Clicked on graphic");
                mAdapter = new PostListAdapter(mContext, mPosts, R.layout.layout_post_list_item_graphic);
//                recyclerView.setLayoutManager(null);
//                recyclerView.setAdapter(null);
                recyclerView.setAdapter(mAdapter);
                recyclerView.setLayoutManager(mLayoutManager);
                break;
            case R.id.thumbnail:
                Log.d(TAG, "onOptionsItemSelected: Clicked on thumbnail");
                mAdapter = new PostListAdapter(mContext, mPosts, R.layout.layout_post_list_item_thumbnail);
//                recyclerView.setLayoutManager(null);
//                recyclerView.setAdapter(null);
                recyclerView.setAdapter(mAdapter);
                recyclerView.setLayoutManager(mLayoutManager);
                break;
            case R.id.verbose:
                Log.d(TAG, "onOptionsItemSelected: Clicked on verbose");
                mAdapter = new PostListAdapter(mContext, mPosts, R.layout.layout_post_list_item_verbose);
//                recyclerView.setLayoutManager(null);
//                recyclerView.setAdapter(null);
                recyclerView.setAdapter(mAdapter);
                recyclerView.setLayoutManager(mLayoutManager);
                break;
            default:
                Log.d(TAG, "onOptionsItemSelected: Nothing clicked in spinner option menu");
        }
        return super.onOptionsItemSelected(item);
    }

}
