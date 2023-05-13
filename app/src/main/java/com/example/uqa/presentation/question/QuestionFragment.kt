package com.example.uqa.presentation.question

import android.app.Dialog
import android.content.Context
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
import com.example.uqa.presentation.home.HomeViewModel


class QuestionFragment : Fragment() {

    private lateinit var binding: FragmentQuestionBinding
    private lateinit var viewModel: QuestionViewModel
    private val args: QuestionFragmentArgs  by navArgs()
    private val question by lazy { args.currentPost }
    private lateinit var answerAdapter: AnswerAdapter

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
        viewModel.getAnswers(question.id)

        setTexts()

        binding.questionUpvoteButton.setOnClickListener {

            question.isUpvoted = !question.isUpvoted

            if (question.isUpvoted) {
                question.upvotes += 1

                if (question.isDownvoted){
                    question.isDownvoted = false
                    question.downvotes -= 1
                    binding.questionDownvoteButton.setColorFilter(Color.parseColor("#C74949"))
                    binding.questionDownvoteNum.setTextColor(resources.getColor(R.color.black))
                }

                binding.questionUpvoteButton.setColorFilter(Color.parseColor("#7fbaff"))
                binding.questionUpvoteNum.setTextColor(Color.parseColor("#7fbaff"))
            } else {
                question.upvotes -= 1
                binding.questionUpvoteButton.setColorFilter(Color.parseColor("#4983C7"))
                binding.questionUpvoteNum.setTextColor(resources.getColor(R.color.black))
            }


            setTexts()
        }

        binding.questionDownvoteButton.setOnClickListener {

            question.isDownvoted = !question.isDownvoted

            if (question.isDownvoted) {
                question.downvotes += 1

                if (question.isUpvoted){
                    question.isUpvoted = false
                    question.upvotes -= 1
                    binding.questionUpvoteButton.setColorFilter(Color.parseColor("#4983C7"))
                    binding.questionUpvoteNum.setTextColor(resources.getColor(R.color.black))
                }

                binding.questionDownvoteButton.setColorFilter(Color.parseColor("#ff8989"))
                binding.questionDownvoteNum.setTextColor(Color.parseColor("#ff8989"))
            } else {
                question.downvotes -= 1
                binding.questionDownvoteButton.setColorFilter(Color.parseColor("#C74949"))
                binding.questionDownvoteNum.setTextColor(resources.getColor(R.color.black))
            }



            setTexts()
        }

        setAdapter()

        binding.commentButton.setOnClickListener {
            setDialog(null)
        }
    }

    private fun setAdapter() {
        answerAdapter = AnswerAdapter()
        binding.answersList.adapter = answerAdapter
        viewModel.answersList.observe(viewLifecycleOwner){list ->
            Log.d(TAG, "answersList: ${list}")
            answerAdapter.submitList(list)
        }

        viewModel.repliesList.observe(viewLifecycleOwner){list ->
            Log.d(TAG, "repliesList: ${list}")
            answerAdapter.setRepliesList(list)
        }

        answerAdapter.onPostClickListener = {
//            Log.d(TAG, "onPostClickListener: ${it.id}")
            setDialog(it.id)
        }
    }

    private fun setTexts() {
        binding.questionTitle.text = question.title
        binding.questionAuthor.text = question.author
        binding.questionDate.text = question.date
        binding.questionUpvoteNum.text = question.upvotes.toString()
        binding.questionDownvoteNum.text = question.downvotes.toString()

        viewModel.answersList.observe(viewLifecycleOwner){list ->
            binding.questionAnswers.text = resources.getString(
                R.string.answers_txt,
                list.size
            )
        }
    }

    private fun setDialog(replyId: Long?){
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogAnsweringBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.answerButton.setOnClickListener {
            Toast.makeText(requireContext(), "Posted", Toast.LENGTH_SHORT).show()

            Log.d(TAG, "setDialog: $replyId")
            val newAnswer = Answer(
                0,
                question.id,
                replyId,
                dialogBinding.inputAnswer.text.toString(),
                dialogBinding.inputAnswerAuthor.text.toString(),
                0,
                0,
                false,
                false
            )

            viewModel.addAnswer(newAnswer)

            dialog.dismiss()
        }

        dialog.show()
    }
}