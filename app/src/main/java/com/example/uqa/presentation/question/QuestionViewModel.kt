package com.example.uqa.presentation.question

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.uqa.data.Answer
import com.example.uqa.data.Post
import com.example.uqa.data.room.PostsRepository
import com.example.uqa.data.room.UQADatabase
import com.example.uqa.presentation.MainActivity.Companion.TAG
import kotlinx.coroutines.launch

class QuestionViewModel(application: Application): AndroidViewModel(application) {

    private val repository: PostsRepository
    val _answersList = MutableLiveData<List<Answer>>()
    val answersList: LiveData<List<Answer>>
        get() = _answersList

    val _repliesList = MutableLiveData<List<Answer>>()
    val repliesList: LiveData<List<Answer>>
        get() = _repliesList

    fun getAnswers(postId: Long){
        viewModelScope.launch {

            val (answers, replies) = repository.getAnswers(postId).partition { it.replyId == null }
            _answersList.postValue(answers)
            _repliesList.postValue(replies)



            Log.d(TAG, "getAnswers _answersList: ${_answersList}")
            Log.d(TAG, "getAnswers _repliesList: ${_repliesList}")
        }
    }

    fun addAnswer(answer: Answer){
        viewModelScope.launch{
            repository.addAnswer(answer)
            getAnswers(answer.postId)
        }
    }

    init {
        val dao = UQADatabase.getDatabase(application).postsDao()
        repository = PostsRepository(dao)

    }


}