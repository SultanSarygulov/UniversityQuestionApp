package com.example.uqa.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val id: Long,
    val title: String,
    val author: String,
    val date: String,
    var upvotes: Int,
    var downvotes: Int
): Parcelable
