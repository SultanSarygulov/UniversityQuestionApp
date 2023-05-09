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

    private lateinit var isUpvotedLiveData: MutableLiveData<Boolean>
    private lateinit var isDownvotedLiveData: MutableLiveData<Boolean>

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

            currentPost.isUpvoted = !currentPost.isUpvoted

            if (currentPost.isUpvoted) {
                currentPost.upvotes += 1

                if (currentPost.isDownvoted){
                    currentPost.isDownvoted = false
                    currentPost.downvotes -= 1
                    binding.questionDownvoteButton.setColorFilter(Color.parseColor("#C74949"))
                    binding.questionDownvoteNum.setTextColor(resources.getColor(R.color.black))
                }

                binding.questionUpvoteButton.setColorFilter(Color.parseColor("#7fbaff"))
                binding.questionUpvoteNum.setTextColor(Color.parseColor("#7fbaff"))
            } else {
                currentPost.upvotes -= 1
                binding.questionUpvoteButton.setColorFilter(Color.parseColor("#4983C7"))
                binding.questionUpvoteNum.setTextColor(resources.getColor(R.color.black))
            }


            setTexts()
        }

        binding.questionDownvoteButton.setOnClickListener {

            currentPost.isDownvoted = !currentPost.isDownvoted

            if (currentPost.isDownvoted) {
                currentPost.downvotes += 1

                if (currentPost.isUpvoted){
                    currentPost.isUpvoted = false
                    currentPost.upvotes -= 1
                    binding.questionUpvoteButton.setColorFilter(Color.parseColor("#4983C7"))
                    binding.questionUpvoteNum.setTextColor(resources.getColor(R.color.black))
                }

                binding.questionDownvoteButton.setColorFilter(Color.parseColor("#ff8989"))
                binding.questionDownvoteNum.setTextColor(Color.parseColor("#ff8989"))
            } else {
                currentPost.downvotes -= 1
                binding.questionDownvoteButton.setColorFilter(Color.parseColor("#C74949"))
                binding.questionDownvoteNum.setTextColor(resources.getColor(R.color.black))
            }



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

    fun pxToDp(px: Int): Int {
        return (px / context?.resources?.displayMetrics?.density!!).toInt()
    }
}