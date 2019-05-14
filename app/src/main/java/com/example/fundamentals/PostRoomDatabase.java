package com.example.fundamentals;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

@Database(entities = {Post.class}, version = 1, exportSchema = false)
public abstract class PostRoomDatabase extends RoomDatabase {

    private static final String TAG = "PostRoomDatabase";

    public abstract PostDao postDao();

    private static PostRoomDatabase INSTANCE;

    public static PostRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PostRoomDatabase.class) {
                if (INSTANCE == null) {
                    // create the database
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PostRoomDatabase.class,
                            "post_database")
                            .addCallback(callback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            Log.d(TAG, "onOpen: Callback opened ");
            super.onOpen(db);
            new populateDBAsyncTask(INSTANCE).execute();
        }
    };

    private static class populateDBAsyncTask extends AsyncTask<Void, Void, Void> {

        PostDao mDao;

        populateDBAsyncTask(PostRoomDatabase db) {
            mDao = db.postDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i <= DummyData.titles.length - 1; i++) {
                Post post = new Post(DummyData.titles[i]);
                post.setUsername(DummyData.usernames[i]);
                post.setDescription(DummyData.descriptions[i]);
                post.setPicturePath(DummyData.picturePaths[i]);
                Log.d(TAG, "doInBackground: Inserting post: " + DummyData.titles[i]);
                mDao.insert(post);
            }
            return null;
        }
    }
}
