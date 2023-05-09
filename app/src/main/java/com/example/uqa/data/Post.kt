package com.example.uqa.data

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "posts_table")
data class Post(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val author: String,
    val date: String,
    var upvotes: Int,
    var downvotes: Int,
    var isUpvoted: Boolean,
    var isDownvoted: Boolean,
): Parcelable
