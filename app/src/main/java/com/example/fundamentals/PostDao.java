package com.example.fundamentals;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PostDao {

    @Insert
    void insert(Post post);

    @Update
    void update(Post post);

    @Delete
    void deletePost(Post post);

    @Query("SELECT * FROM post_table ORDER BY date_created DESC")
    LiveData<List<Post>> getAllPosts();
}
