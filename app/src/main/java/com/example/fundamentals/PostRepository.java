package com.example.fundamentals;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

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

    public void insert(Post post) {new InsertAsyncTask(mPostDao).execute(post);}
    public void deletePost(Post post) {new DeletePostAsyncTask(mPostDao).execute(post);}
    public void updatePost(Post post) {new UpdatePostAsyncTask(mPostDao).execute(post);}


    private static class InsertAsyncTask extends AsyncTask<Post, Void, Void> {

        PostDao mDao;

        public InsertAsyncTask(PostDao mDao) {
            this.mDao = mDao;
        }

        @Override
        protected Void doInBackground(Post... posts) {
            mDao.insert(posts[0]);
            return null;
        }
    }

    private static class DeletePostAsyncTask extends AsyncTask<Post, Void, Void> {

        PostDao mDao;

        public DeletePostAsyncTask(PostDao mDao) {
            this.mDao = mDao;
        }

        @Override
        protected Void doInBackground(Post... posts) {
            mDao.deletePost(posts[0]);
            return null;
        }
    }

    private static class UpdatePostAsyncTask extends AsyncTask<Post, Void, Void> {

        PostDao mDao;

        public UpdatePostAsyncTask(PostDao mDao) {
            this.mDao = mDao;
        }

        @Override
        protected Void doInBackground(Post... posts) {
            mDao.update(posts[0]);
            return null;
        }
    }

}
