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

    class AnswerViewHolder(item: View): RecyclerView.ViewHolder(item){
        val binding = ItemAnswerBinding.bind(item)

        fun bind(answer: Answer) = with(binding){
            binding.answerText.text = answer.text
            binding.answerAuthor.text = answer.author
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_answer, parent, false)

        return AnswerViewHolder(view)
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
}