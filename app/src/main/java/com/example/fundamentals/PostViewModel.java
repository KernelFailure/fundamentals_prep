package com.example.fundamentals;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class PostViewModel extends AndroidViewModel {

    private PostRepository mRepository;
    private LiveData<List<Post>> mAllPosts;

    public PostViewModel(@NonNull Application application) {
        super(application);
        mRepository = new PostRepository(application);
        mAllPosts = mRepository.getAllPosts();
    }

    public void insert(Post post) {mRepository.insert(post);}
    public void deletePost(Post post) {mRepository.deletePost(post);}
    public void updatePost(Post post) {mRepository.updatePost(post);}
    public LiveData<List<Post>> getAllPosts() {return mRepository.getAllPosts();}
}
