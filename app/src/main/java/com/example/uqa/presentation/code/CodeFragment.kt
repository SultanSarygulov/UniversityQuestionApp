package com.example.uqa.presentation.code

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.uqa.R
import com.example.uqa.databinding.FragmentCodeBinding
import com.example.uqa.databinding.FragmentRegisterBinding
import com.example.uqa.presentation.register.RegisterFragmentDirections

class CodeFragment : Fragment() {

    private lateinit var binding: FragmentCodeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.confirmCodeButton.setOnClickListener {

            val authorizeAction = CodeFragmentDirections.actionCodeFragmentToHomeFragment()
            findNavController().navigate(authorizeAction)
        }
    }

}