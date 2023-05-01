package com.example.uqa.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.uqa.R
import com.example.uqa.data.Post
import com.example.uqa.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.getPostsList()

        setAdapter()
//
//        binding.questionButton.setOnClickListener {
//            findNavController().navigate(R.id.action_homeFragment_to_questionFragment)
//        }

//        binding.askButton.setOnClickListener {
//            findNavController().navigate(R.id.action_homeFragment_to_askQuestionFragment)
//        }
    }

    private fun setAdapter() {
        val postAdapter = PostAdapter()
        binding.mainCarousel.adapter = postAdapter
        viewModel.postsList.observe(viewLifecycleOwner){postsList ->
            postAdapter.submitList(postsList)
        }


        postAdapter.onPostClockListener = {post ->

            val action = HomeFragmentDirections.actionHomeFragmentToQuestionFragment(post)
            findNavController().navigate(action)
        }
    }
}