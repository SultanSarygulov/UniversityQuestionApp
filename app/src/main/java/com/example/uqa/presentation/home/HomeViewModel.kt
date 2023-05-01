package com.example.uqa.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.uqa.data.Post

class HomeViewModel: ViewModel() {

    val _postsList = MutableLiveData<List<Post>>()
    val postsList: LiveData<List<Post>>
        get() = _postsList

    fun getPostsList(){
        _postsList.postValue(
            listOf(
                Post(0, "When is IRO open?", "Eles_Akin", "23/12/23", 1, 0),
                Post(1, "Where can I find mr Remudin?", "Abdurahman", "23/12/23", 2, 1),
                Post(2, "COM21, do you have the schedule of fina; exams?", "Daniil Krip", "23/12/23", 3, 5),
                Post(3, "Test4", "Chuvash", "23/12/23", 4, 1),
                Post(4, "Test5", "Ramzes666", "23/12/23", 5, 9),
                Post(5, "Test6", "Ruslan Isaev", "23/12/23", 6, 1),
            )
        )
    }
}