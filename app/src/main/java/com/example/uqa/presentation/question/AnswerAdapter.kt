package com.example.uqa.presentation.question

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.uqa.R
import com.example.uqa.data.Answer
import com.example.uqa.databinding.ItemAnswerBinding
import com.example.uqa.presentation.MainActivity.Companion.TAG

class AnswerAdapter: ListAdapter<Answer, AnswerAdapter.AnswerViewHolder>(AnswerDiffUtil()) {

    var onPostClickListener: ((Answer) -> Unit)? = null

    private var repliesList = mutableListOf<Answer>()
    fun setRepliesList(replies: List<Answer>){
        val diffCallback = ReplyDiffUtil(oldList = repliesList, newList = replies)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        repliesList.clear()
        repliesList.addAll(replies)
        diffResult.dispatchUpdatesTo(this)
    }


    class AnswerViewHolder(
        item: View,
        private val repliesList: List<Answer>,
        private val onPostClickListener: ((Answer) -> Unit)?
    ): RecyclerView.ViewHolder(item){

        private val binding = ItemAnswerBinding.bind(item)

        fun bind(answer: Answer) = with(binding){
            binding.answerText.text = answer.text
            binding.answerAuthor.text = answer.author

            val repliesListLiveData= MutableLiveData<List<Answer>> ()
            repliesListLiveData.postValue(repliesList)

            val replyAdapter = AnswerAdapter()
            binding.replyList.adapter = replyAdapter
            repliesListLiveData.observe(itemView.context as LifecycleOwner){ list ->
                replyAdapter.submitList(list.filter { it.replyId == answer.id })
            }



            binding.replyButton.setOnClickListener {
                onPostClickListener?.invoke(answer)
            }

            binding.answerUpvotes.text = answer.upvotes.toString()

            binding.answerDownvotes.text = answer.downvotes.toString()

            binding.answerUpvoteButton.setOnClickListener {
                answer.isUpvoted = !answer.isUpvoted

                if (answer.isUpvoted) {
                    answer.upvotes += 1

                    if (answer.isDownvoted){
                        answer.isDownvoted = false
                        answer.downvotes -= 1
                        binding.answerDownvoteButton.setColorFilter(Color.parseColor("#C74949"))
                        binding.answerDownvotes.setTextColor(Color.parseColor("#000000"))
                    }

                    binding.answerUpvoteButton.setColorFilter(Color.parseColor("#7fbaff"))
                    binding.answerUpvotes.setTextColor(Color.parseColor("#7fbaff"))
                } else {
                    answer.upvotes -= 1
                    binding.answerUpvoteButton.setColorFilter(Color.parseColor("#4983C7"))
                    binding.answerUpvotes.setTextColor(Color.parseColor("#000000"))
                }

                binding.answerUpvotes.text = answer.upvotes.toString()
                binding.answerDownvotes.text = answer.downvotes.toString()
            }

            binding.answerDownvoteButton.setOnClickListener {
                answer.isDownvoted = !answer.isDownvoted

                if (answer.isDownvoted) {
                    answer.downvotes += 1

                    if (answer.isUpvoted){
                        answer.isUpvoted = false
                        answer.upvotes -= 1
                        binding.answerUpvoteButton.setColorFilter(Color.parseColor("#4983C7"))
                        binding.answerUpvotes.setTextColor(Color.parseColor("#000000"))
                    }

                    binding.answerDownvoteButton.setColorFilter(Color.parseColor("#ff8989"))
                    binding.answerDownvotes.setTextColor(Color.parseColor("#ff8989"))
                } else {
                    answer.downvotes -= 1
                    binding.answerDownvoteButton.setColorFilter(Color.parseColor("#C74949"))
                    binding.answerDownvotes.setTextColor(Color.parseColor("#000000"))
                }

                binding.answerUpvotes.text = answer.upvotes.toString()
                binding.answerDownvotes.text = answer.downvotes.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_answer, parent, false)

        return AnswerViewHolder(view, repliesList, onPostClickListener)
    }

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        val item = getItem(position)
        Log.d(TAG, "onBindViewHolder: ${item}")
        holder.bind(item)

    }

    class AnswerDiffUtil: DiffUtil.ItemCallback<Answer>(){
        override fun areItemsTheSame(oldItem: Answer, newItem: Answer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Answer, newItem: Answer): Boolean {
            return oldItem == newItem
        }

    }

    class ReplyDiffUtil(
        private val oldList: List<Answer>,
        private val newList: List<Answer>
        ): DiffUtil.Callback(){

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

    }
}