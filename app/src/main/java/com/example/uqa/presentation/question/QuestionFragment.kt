package com.example.uqa.presentation.question

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.uqa.R
import com.example.uqa.data.Answer
import com.example.uqa.databinding.DialogAnsweringBinding
import com.example.uqa.databinding.FragmentQuestionBinding
import com.example.uqa.presentation.MainActivity.Companion.TAG
import com.example.uqa.presentation.MainActivity.Companion.USERNAME
import com.example.uqa.presentation.home.HomeViewModel


class QuestionFragment : Fragment() {

    private lateinit var binding: FragmentQuestionBinding
    private lateinit var viewModel: QuestionViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private val args: QuestionFragmentArgs by navArgs()
    private val question by lazy { args.currentPost }
    private lateinit var answerAdapter: AnswerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[QuestionViewModel::class.java]
        viewModel.getAnswers(question.id)

        setTexts()
        setupVoteButtons()
        setAdapter()

        binding.commentButton.setOnClickListener { showAnswerDialog(null) }
    }

    private fun setupVoteButtons() {
        binding.questionUpvoteButton.setOnClickListener {
            toggleVote(isUpvote = true)
        }

        binding.questionDownvoteButton.setOnClickListener {
            toggleVote(isUpvote = false)
        }
    }

    private fun toggleVote(isUpvote: Boolean) {
        if (isUpvote) {
            question.isUpvoted = !question.isUpvoted
            if (question.isUpvoted) {
                question.upvotes++
                if (question.isDownvoted) {
                    question.isDownvoted = false
                    question.downvotes--
                }
            } else {
                question.upvotes--
            }
        } else {
            question.isDownvoted = !question.isDownvoted
            if (question.isDownvoted) {
                question.downvotes++
                if (question.isUpvoted) {
                    question.isUpvoted = false
                    question.upvotes--
                }
            } else {
                question.downvotes--
            }
        }
        updateVoteUI()
        setTexts()
    }

    private fun updateVoteUI() {
        binding.questionUpvoteButton.setColorFilter(
            if (question.isUpvoted) Color.parseColor("#7fbaff") else Color.parseColor("#4983C7")
        )
        binding.questionUpvoteNum.setTextColor(
            if (question.isUpvoted) Color.parseColor("#7fbaff") else resources.getColor(R.color.black)
        )

        binding.questionDownvoteButton.setColorFilter(
            if (question.isDownvoted) Color.parseColor("#ff8989") else Color.parseColor("#C74949")
        )
        binding.questionDownvoteNum.setTextColor(
            if (question.isDownvoted) Color.parseColor("#ff8989") else resources.getColor(R.color.black)
        )
    }

    private fun setAdapter() {
        answerAdapter = AnswerAdapter()
        binding.answersList.adapter = answerAdapter

        viewModel.answersList.observe(viewLifecycleOwner) { list ->
            answerAdapter.submitList(list.toMutableList()) // Submit a new mutable copy
        }

        viewModel.repliesList.observe(viewLifecycleOwner) { list ->
            answerAdapter.updateRepliesList(list)
        }

        answerAdapter.onPostClickListener = { answer ->
            showAnswerDialog(answer.id) // Extracting `id` from the `Answer` object
        }
    }

    private fun setTexts() {
        binding.apply {
            questionTitle.text = question.title
            questionAuthor.text = question.author
            questionDate.text = question.date
            questionUpvoteNum.text = question.upvotes.toString()
            questionDownvoteNum.text = question.downvotes.toString()
        }

        viewModel.answersList.observe(viewLifecycleOwner) { list ->
            binding.questionAnswers.text = resources.getString(
                R.string.answers_txt,
                list.size
            )
        }
    }

    private fun showAnswerDialog(replyId: Long?) {
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogAnsweringBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.answerButton.setOnClickListener {
            val newAnswer = Answer(
                id = 0,
                postId = question.id,
                replyId = replyId,
                text = dialogBinding.inputAnswer.text.toString(),
                author = sharedPreferences.getString(USERNAME, "error").toString(),
                upvotes = 0,
                downvotes = 0,
                isUpvoted = false,
                isDownvoted = false
            )

            viewModel.addAnswer(newAnswer)
            Toast.makeText(requireContext(), "Posted", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }
}
