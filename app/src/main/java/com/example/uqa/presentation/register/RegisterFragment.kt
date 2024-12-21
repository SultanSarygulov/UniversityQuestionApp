package com.example.uqa.presentation.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.uqa.R
import com.example.uqa.databinding.FragmentAskQuestionBinding
import com.example.uqa.databinding.FragmentRegisterBinding
import com.example.uqa.presentation.askQuestion.AskQuestionViewModel
import com.example.uqa.presentation.login.LoginFragmentDirections

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerButton.setOnClickListener {

            val codeAction = RegisterFragmentDirections.actionRegisterFragmentToCodeFragment()
            findNavController().navigate(codeAction)
        }

        binding.alreadyRegistredText.setOnClickListener {

            val loginAction = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            findNavController().navigate(loginAction)
        }
    }
}