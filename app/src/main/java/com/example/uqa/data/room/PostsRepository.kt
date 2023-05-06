package com.example.uqa.data.room

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.uqa.data.Answer
import com.example.uqa.data.Post
import com.example.uqa.presentation.MainActivity.Companion.TAG

class PostsRepository(private val postsDao: PostsDao) {

    suspend fun getPosts(): List<Post>{
        return postsDao.getPosts()
    }

    suspend fun addPosts(post: Post){
        postsDao.addPost(post)
    }

    suspend fun getAnswers(postId: Long): List<Answer>{
        return postsDao.getAnswers(postId)
    }

    suspend fun addAnswer(answer: Answer){
        postsDao.addAnswer(answer)
    }
}