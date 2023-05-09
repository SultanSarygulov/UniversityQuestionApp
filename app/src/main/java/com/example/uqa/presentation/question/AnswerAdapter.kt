package com.example.uqa.presentation.question

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.uqa.R
import com.example.uqa.data.Answer
import com.example.uqa.databinding.ItemAnswerBinding

class AnswerAdapter: ListAdapter<Answer, AnswerAdapter.AnswerViewHolder>(AnswerDiffUtil()) {

    private var repliesList = mutableListOf<Answer>()
    fun setRepliesList(replies: List<Answer>){
        val diffCallback = ReplyDiffUtil(oldList = repliesList, newList = replies)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        repliesList.clear()
        repliesList.addAll(replies)
        diffResult.dispatchUpdatesTo(this)
    }



    class AnswerViewHolder(item: View, private val repliesList: List<Answer>): RecyclerView.ViewHolder(item){
        val binding = ItemAnswerBinding.bind(item)

        fun bind(answer: Answer) = with(binding){
            binding.answerText.text = answer.text
            binding.answerAuthor.text = answer.author

            if (answer.replyId == null){
                val replyAdapter = AnswerAdapter()
                binding.replyList.adapter = replyAdapter
                replyAdapter.submitList(repliesList.filter { it.replyId == answer.id })
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_answer, parent, false)

        return AnswerViewHolder(view, repliesList)
    }

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        holder.bind(getItem(position))
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