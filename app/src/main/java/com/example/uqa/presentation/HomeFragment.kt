package com.example.uqa.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.uqa.R
import com.example.uqa.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.questionButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_questionFragment)
        }

        binding.askButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_askQuestionFragment)
        }
    }
}