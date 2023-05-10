package com.example.uqa.presentation.askQuestion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.uqa.R
import com.example.uqa.data.Post
import com.example.uqa.databinding.FragmentAskQuestionBinding
import com.example.uqa.databinding.FragmentQuestionBinding
import com.example.uqa.presentation.home.HomeViewModel
import java.text.SimpleDateFormat
import java.util.*


class AskQuestionFragment : Fragment() {

    private lateinit var binding: FragmentAskQuestionBinding
    private lateinit var viewModel: AskQuestionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAskQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[AskQuestionViewModel::class.java]

        binding.postButton.setOnClickListener {
            checkInputs()
        }
    }

    private fun checkInputs() {
        if (
            binding.inputPostTextET.text.toString().isEmpty() ||
            binding.inputName.text.toString().isEmpty()){
            Toast.makeText(requireContext(), "Fill out all boxes!", Toast.LENGTH_SHORT).show()
            return
        }

        postQuestion()
    }

    private fun postQuestion() {
        val newPost = Post(
            0,
            binding.inputPostTextET.text.toString(),
            binding.inputName.text.toString(),
            getDate(),
            0,
            0,
            isUpvoted = false,
            isDownvoted = false
        )

        viewModel.addPost(newPost)

        Toast.makeText(requireContext(), "Question successfully posted!", Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()

    }

    private fun getDate(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val todayDate = Date()
        val formattedDate = dateFormat.format(todayDate)

        return formattedDate
    }
}