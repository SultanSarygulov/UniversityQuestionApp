package com.example.uqa.presentation.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.uqa.data.Post
import com.example.uqa.data.room.PostsRepository
import com.example.uqa.data.room.UQADatabase
import com.example.uqa.presentation.MainActivity.Companion.TAG
import kotlinx.coroutines.launch

class HomeViewModel(application: Application): AndroidViewModel(application) {

    private var _postsList = MutableLiveData<List<Post>>()
    val postsList: LiveData<List<Post>>
        get() = _postsList


    fun getPostsList(){
        viewModelScope.launch {
            _postsList.postValue(repository.getPosts())
            Log.d(TAG, "getPostsList: ${_postsList.value}")
        }

    }

    fun addPost(post: Post){
        viewModelScope.launch {
            repository.addPosts(post)
            Log.d(TAG, "addPost: $post")
        }
    }

    private val repository: PostsRepository

    init {
        val postsDao = UQADatabase.getDatabase(application).postsDao()
        repository = PostsRepository(postsDao)
    }






}