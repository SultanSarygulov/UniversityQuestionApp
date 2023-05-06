package com.example.uqa.presentation.question

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.uqa.R
import com.example.uqa.data.Answer
import com.example.uqa.databinding.DialogAnsweringBinding
import com.example.uqa.databinding.FragmentQuestionBinding
import com.example.uqa.presentation.MainActivity.Companion.TAG
import com.example.uqa.presentation.home.HomeViewModel


class QuestionFragment : Fragment() {

    private lateinit var binding: FragmentQuestionBinding
    private lateinit var viewModel: QuestionViewModel
    private val args: QuestionFragmentArgs  by navArgs()
    private val currentPost by lazy { args.currentPost }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[QuestionViewModel::class.java]
        viewModel.getAnswers(currentPost.id)

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

        binding.commentButton.setOnClickListener {
            setDialog()
        }
    }

    private fun setAdapter() {
        val answerAdapter = AnswerAdapter()
        binding.answersList.adapter = answerAdapter
        viewModel.answersList.observe(viewLifecycleOwner){list ->
            Log.d(TAG, "setAdapter: ${list}")
            answerAdapter.submitList(list)
        }

    }

    private fun setTexts() {
        binding.questionTitle.text = currentPost.title
        binding.questionAuthor.text = currentPost.author
        binding.questionDate.text = currentPost.date
        binding.questionUpvoteNum.text = currentPost.upvotes.toString()
        binding.questionDownvoteNum.text = currentPost.downvotes.toString()

        viewModel.answersList.observe(viewLifecycleOwner){list ->
            binding.questionAnswers.text = resources.getString(
                R.string.answers_txt,
                list.size
            )
        }

    }

    private fun setDialog(){
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogAnsweringBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.answerButton.setOnClickListener {
            Toast.makeText(requireContext(), "Posted", Toast.LENGTH_SHORT).show()

            val newAnswer = Answer(
                0,
                currentPost.id,
                dialogBinding.inputAnswer.text.toString(),
                dialogBinding.inputAnswerAuthor.text.toString(),
            )

            viewModel.addAnswer(newAnswer)

            dialog.dismiss()
        }

        dialog.show()
    }
}