package com.example.uqa.data.room

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.uqa.data.Post
import com.example.uqa.presentation.MainActivity.Companion.TAG

class PostsRepository(private val postsDao: PostsDao) {

    suspend fun getPosts(): List<Post>{
        Log.d(TAG, "getPosts: ${postsDao.getPosts()}")
        return postsDao.getPosts()
    }

    suspend fun addPosts(post: Post){
        postsDao.addPost(post)
    }
}