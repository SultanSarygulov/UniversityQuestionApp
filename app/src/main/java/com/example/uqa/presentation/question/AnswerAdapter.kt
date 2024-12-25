package com.example.uqa.presentation.question

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.uqa.data.Answer
import com.example.uqa.databinding.ItemAnswerBinding
import com.example.uqa.presentation.MainActivity.Companion.TAG

class AnswerAdapter : ListAdapter<Answer, AnswerAdapter.AnswerViewHolder>(AnswerDiffUtil()) {

    var onPostClickListener: ((Answer) -> Unit)? = null
    private val repliesMap = mutableMapOf<Long, List<Answer>>() // Map of parent answer ID to its replies

    fun updateRepliesList(replies: List<Answer>) {
        repliesMap.clear()
        replies.groupBy { it.replyId }.forEach { (key, value) ->
            if (key != null) repliesMap[key] = value
        }
        notifyDataSetChanged()
    }

    class AnswerViewHolder(
        private val binding: ItemAnswerBinding,
        private val onPostClickListener: ((Answer) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        private val replyAdapter = ReplyAdapter()

        fun bind(answer: Answer, replies: List<Answer>?) {
            binding.apply {
                answerText.text = answer.text
                answerAuthor.text = answer.author
                answerUpvotes.text = answer.upvotes.toString()
                answerDownvotes.text = answer.downvotes.toString()

                // Set up replies RecyclerView
                replyList.adapter = replyAdapter
                replyAdapter.submitList(replies ?: emptyList())

                // Handle reply button click
                replyButton.setOnClickListener {
                    onPostClickListener?.invoke(answer)
                }

                // Handle upvote and downvote button clicks
                answerUpvoteButton.setOnClickListener { toggleUpvote(answer) }
                answerDownvoteButton.setOnClickListener { toggleDownvote(answer) }

                // Update UI based on vote status
                updateVoteUI(answer)
            }
        }

        private fun toggleUpvote(answer: Answer) {
            answer.isUpvoted = !answer.isUpvoted
            if (answer.isUpvoted) {
                answer.upvotes++
                if (answer.isDownvoted) {
                    answer.isDownvoted = false
                    answer.downvotes--
                }
            } else {
                answer.upvotes--
            }
            updateVoteUI(answer)
        }

        private fun toggleDownvote(answer: Answer) {
            answer.isDownvoted = !answer.isDownvoted
            if (answer.isDownvoted) {
                answer.downvotes++
                if (answer.isUpvoted) {
                    answer.isUpvoted = false
                    answer.upvotes--
                }
            } else {
                answer.downvotes--
            }
            updateVoteUI(answer)
        }

        private fun updateVoteUI(answer: Answer) {
            binding.apply {
                // Upvote UI
                answerUpvoteButton.setColorFilter(
                    if (answer.isUpvoted) Color.parseColor("#7fbaff") else Color.parseColor("#4983C7")
                )
                answerUpvotes.setTextColor(
                    if (answer.isUpvoted) Color.parseColor("#7fbaff") else Color.BLACK
                )

                // Downvote UI
                answerDownvoteButton.setColorFilter(
                    if (answer.isDownvoted) Color.parseColor("#ff8989") else Color.parseColor("#C74949")
                )
                answerDownvotes.setTextColor(
                    if (answer.isDownvoted) Color.parseColor("#ff8989") else Color.BLACK
                )

                // Update vote counts
                answerUpvotes.text = answer.upvotes.toString()
                answerDownvotes.text = answer.downvotes.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val binding = ItemAnswerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnswerViewHolder(binding, onPostClickListener)
    }

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        val item = getItem(position)
        val replies = repliesMap[item.id] // Get replies for the current answer
        holder.bind(item, replies)
    }

    class AnswerDiffUtil : DiffUtil.ItemCallback<Answer>() {
        override fun areItemsTheSame(oldItem: Answer, newItem: Answer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Answer, newItem: Answer): Boolean {
            return oldItem == newItem
        }
    }
}

class ReplyAdapter : ListAdapter<Answer, ReplyAdapter.ReplyViewHolder>(AnswerAdapter.AnswerDiffUtil()) {

    class ReplyViewHolder(private val binding: ItemAnswerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(reply: Answer) {
            binding.apply {
                answerText.text = reply.text
                answerAuthor.text = reply.author
                answerUpvotes.text = reply.upvotes.toString()
                answerDownvotes.text = reply.downvotes.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplyViewHolder {
        val binding = ItemAnswerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReplyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReplyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
