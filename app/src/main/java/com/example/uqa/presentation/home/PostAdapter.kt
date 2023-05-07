package com.example.uqa.presentation.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.uqa.R
import com.example.uqa.data.Post
import com.example.uqa.databinding.ItemPostBinding
import com.example.uqa.presentation.MainActivity.Companion.TAG
import java.util.*

class PostAdapter: ListAdapter<Post, PostAdapter.PostViewHolder>(PostDiffUtil()) {

    var unfilteredList = listOf<Post>()

    var onPostClockListener: ((Post) -> Unit)? = null

    class PostViewHolder(
            view: View,
            private val onPostClockListener: ((Post) -> Unit)?
    ): RecyclerView.ViewHolder(view) {
        private val binding = ItemPostBinding.bind(view)

        fun bind(post: Post) = with(binding){
            postTitle.text = post.title
            postAuthor.text = "by ${post.author}"
            postDate.text = post.date
            upvoteNum.text = post.upvotes.toString()
            downvoteNum.text = post.downvotes.toString()

            upvoteButton.setOnClickListener {
                post.upvotes += 1
            }

            downvoteButton.setOnClickListener {
                post.downvotes += 1
            }

            binding.itemPost.setOnClickListener {
                onPostClockListener?.invoke(post)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view, onPostClockListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PostDiffUtil: DiffUtil.ItemCallback<Post>(){
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }

    }

    fun modifyList(list: List<Post>){
        unfilteredList = list
        submitList(list)
    }

    fun filter(query: CharSequence){
        val list = mutableListOf<Post>()

        if (!query.isNullOrEmpty()){
            list.addAll(unfilteredList.filter {
                it.title.lowercase(Locale.getDefault()).contains(query.toString().lowercase(Locale.getDefault()))
            })
        } else {
            list.addAll(unfilteredList)
        }
        Log.d(TAG, "filter: ${list}")

        submitList(list)
    }

}