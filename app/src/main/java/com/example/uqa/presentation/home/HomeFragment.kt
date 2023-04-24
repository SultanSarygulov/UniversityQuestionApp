package com.example.uqa.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.uqa.R
import com.example.uqa.data.Post
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

        setAdapter()
//
//        binding.questionButton.setOnClickListener {
//            findNavController().navigate(R.id.action_homeFragment_to_questionFragment)
//        }

        binding.askButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_askQuestionFragment)
        }
    }

    private fun setAdapter() {
        val postAdapter = PostAdapter()
        binding.mainCarousel.adapter = postAdapter
        postAdapter.submitList(listOf(
            Post(0, "Test1", "Eles_Akin", "23/12/23", 1, 0),
            Post(1, "Test2", "Abdurahman", "23/12/23", 2, 1),
            Post(2, "Test3", "Daniil Krip", "23/12/23", 3, 5),
            Post(3, "Test4", "Chuvash", "23/12/23", 4, 1),
            Post(4, "Test5", "Ramzes666", "23/12/23", 5, 9),
            Post(5, "Test6", "Ruslan Isaev", "23/12/23", 6, 1),
        ))

        postAdapter.onPostClockListener = {post ->

            val action = HomeFragmentDirections.actionHomeFragmentToQuestionFragment(post)
            findNavController().navigate(action)
        }
    }
}