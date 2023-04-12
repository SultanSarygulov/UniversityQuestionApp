package com.example.uqa.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.uqa.R
import com.example.uqa.databinding.FragmentAskQuestionBinding
import com.example.uqa.databinding.FragmentQuestionBinding


class AskQuestionFragment : Fragment() {

    private lateinit var binding: FragmentAskQuestionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAskQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }
}