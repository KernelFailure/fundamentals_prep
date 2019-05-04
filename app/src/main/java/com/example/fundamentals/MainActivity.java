package com.example.fundamentals;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    String[] picturePaths = {"https://www.screengeek.net/wp-content/uploads/2018/01/batman-dc-comics.jpg", "https://static0.srcdn.com/wordpress/wp-content/uploads/2018/10/Superman-in-Heroes-Crisis-DC-Comic.jpg", "https://www.factinate.com/wp-content/uploads/2018/09/wonder-woman-batman-superman.jpg", "https://cdn2us.denofgeek.com/sites/denofgeekus/files/styles/main_wide/public/2019/03/the-flash-71-year-one-cover.jpg?itok=zjzL4IVB", "https://cdn.vox-cdn.com/thumbor/aEg3hBWEuCcgbdUZdS7PLw30Y8g=/0x0:2000x3037/1200x675/filters:focal(814x986:1134x1306)/cdn.vox-cdn.com/uploads/chorus_image/image/62709931/IMG_0246.0.jpg"};
    String[] descriptions = {"Batman is a superhero appearing in American comic books published by DC Comics. The character was created by artist Bob Kane and writer Bill Finger,[1][2] and first appeared in Detective Comics #27 in 1939. Originally named the Bat-Man, the character is also referred to by such epithets as the Caped Crusader, the Dark Knight, and the World's Greatest Detective.",
        "Superman is a fictional character, a superhero appearing in American comic books published by DC Comics. Created by writer Jerry Siegel and artist Joe Shuster, the character first appeared in Action Comics #1 on April 18, 1938 which marked the rise of the Golden Age of Comic Books",
        "Wonder Woman is a fictional superhero appearing in American comic books published by DC Comics. The character is a founding member of the Justice League. The character first appeared in All Star Comics #8 in October 1941[1] with her first feature in Sensation Comics #1, January 1942. The Wonder Woman title has been published by DC Comics almost continuously except for a brief hiatus in 1986.[3] In her homeland, the island nation of Themyscira, her official title is Princess Diana of Themyscira, Daughter of Hippolyta. When blending into the society outside of her homeland, she adopts her civilian identity Diana Prince",
        "The Flash (or simply Flash) is the name of several superheroes appearing in American comic books published by DC Comics. Created by writer Gardner Fox and artist Harry Lampert, the original Flash first appeared in Flash Comics #1 (cover date January 1940/release month November 1939).[1] Nicknamed the Scarlet Speedster, all incarnations of the Flash possess super speed, which includes the ability to run, move, and think extremely fast, use superhuman reflexes, and seemingly violate certain laws of physics",
        "Aquaman is a fictional superhero appearing in American comic books published by DC Comics. Created by Paul Norris and Mort Weisinger, the character debuted in More Fun Comics #73 (November 1941).[1] Initially a backup feature in DC's anthology titles, Aquaman later starred in several volumes of a solo comic book series. During the late 1950s and 1960s superhero-revival period known as the Silver Age, he was a founding member of the Justice League. In the 1990s Modern Age, writers interpreted Aquaman's character more seriously, with storylines depicting the weight of his role as king of Atlantis"};


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

    // widgets
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        itemLayout = mPreferences.getInt(LAYOUT_KEY, R.layout.layout_post_list_item_graphic);

        mPosts = new ArrayList<>();
        for (int i = 0; i <= titles.length - 1; i++) {
            Post post = new Post(titles[i]);
            post.setUsername(usernames[i]);
            post.setPicturePath(picturePaths[i]);
            post.setDescription(descriptions[i]);
            mPosts.add(post);
        }
        mLayoutManager = new LinearLayoutManager(this);

        if (savedInstanceState == null) {
            Log.d(TAG, "onCreate: Saved Instance was null");
            mAdapter = new PostListAdapter(this, mPosts, itemLayout);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(mAdapter);
        } else {
            Log.d(TAG, "onCreate: Something was in saved instance");
            itemLayout = savedInstanceState.getInt(LAYOUT_RESOURCE_INT);
            mAdapter = new PostListAdapter(mContext, mPosts, itemLayout);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setLayoutManager(mLayoutManager);
        }
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
