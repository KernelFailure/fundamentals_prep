package com.example.fundamentals;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Post.class}, version = 1, exportSchema = false)
public abstract class PostRoomDatabase extends RoomDatabase {

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
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
