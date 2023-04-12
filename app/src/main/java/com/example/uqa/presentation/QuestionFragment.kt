package com.example.uqa.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.uqa.R
import com.example.uqa.databinding.FragmentHomeBinding
import com.example.uqa.databinding.FragmentQuestionBinding


class QuestionFragment : Fragment() {

    private lateinit var binding: FragmentQuestionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }
}