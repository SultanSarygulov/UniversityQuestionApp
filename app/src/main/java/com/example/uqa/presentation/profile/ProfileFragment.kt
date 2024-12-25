package com.example.uqa.presentation.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.uqa.R
import com.example.uqa.databinding.FragmentProfileBinding
import com.example.uqa.databinding.FragmentRegisterBinding
import com.example.uqa.presentation.MainActivity.Companion.USERNAME


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = sharedPreferences.getString(USERNAME, "error")
        binding.userName.setText(username)

        binding.logOutButton.setOnClickListener {

            val action = ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }
}