package com.example.uqa.presentation.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.uqa.R
import com.example.uqa.databinding.FragmentLoginBinding
import com.example.uqa.databinding.FragmentQuestionBinding


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {

            val homeAction = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
            findNavController().navigate(homeAction)
        }

        binding.noAccountText.setOnClickListener {

            val registerAction = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(registerAction)
        }
    }


}