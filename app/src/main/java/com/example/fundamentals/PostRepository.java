package com.example.fundamentals;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class PostRepository {

    private PostDao mPostDao;
    private LiveData<List<Post>> mAllPosts;

    public PostRepository(Application application) {
        PostRoomDatabase db = PostRoomDatabase.getDatabase(application);
        mPostDao = db.postDao();
        mAllPosts = mPostDao.getAllPosts();
    }

    LiveData<List<Post>> getAllPosts() {return mAllPosts;}

    public void insert(Post post) {mPostDao.insert(post);}
    public void deletePost(Post post) {mPostDao.deletePost(post);}
    public void updatePost(Post post) {mPostDao.update(post);}


}
