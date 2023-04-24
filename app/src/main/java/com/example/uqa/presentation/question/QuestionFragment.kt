package com.example.uqa.presentation.question

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.navigation.fragment.navArgs
import com.example.uqa.R
import com.example.uqa.data.Answer
import com.example.uqa.databinding.FragmentHomeBinding
import com.example.uqa.databinding.FragmentQuestionBinding


class QuestionFragment : Fragment() {

    private lateinit var binding: FragmentQuestionBinding
    private val args: QuestionFragmentArgs  by navArgs()
    private val currentPost by lazy { args.currentPost }

    private val answersList = listOf(
        Answer(0, "1", "Mongul Bek"),
        Answer(1, "2", "EShlere"),
        Answer(2, "3", "Bob"),
        Answer(3, "4", "Sultan V"),
        Answer(4, "I'm Gay", "John Yag"),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTexts()

        binding.questionUpvoteButton.setOnClickListener {
            currentPost.upvotes += 1

            setTexts()
        }

        binding.questionDownvoteButton.setOnClickListener {
            currentPost.downvotes += 1

            setTexts()
        }

        setAdapter()
    }

    private fun setAdapter() {
        val answerAdapter = AnswerAdapter()
        binding.answersList.adapter = answerAdapter
        answerAdapter.submitList(answersList)
    }

    private fun setTexts() {
        binding.questionTitle.text = currentPost.title
        binding.questionAuthor.text = currentPost.author
        binding.questionDate.text = currentPost.date
        binding.questionUpvoteNum.text = currentPost.upvotes.toString()
        binding.questionDownvoteNum.text = currentPost.downvotes.toString()

        binding.questionAnswers.text = resources.getString(
            R.string.answers_txt,
            answersList.size
        )
    }
}