package com.example.uqa.presentation.home

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.uqa.data.Post
import com.example.uqa.databinding.FragmentHomeBinding
import com.example.uqa.presentation.MainActivity.Companion.PREFERENCE_KEY
import com.example.uqa.presentation.MainActivity.Companion.TAG


class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var postAdapter: PostAdapter

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
//        getPosts()


        setAdapter()
        viewModel.getPostsList()

        binding.searchView.setOnQueryTextListener(this)



    }



    private fun setAdapter() {
        postAdapter = PostAdapter()
        binding.mainCarousel.adapter = postAdapter
        viewModel.postsList.observe(viewLifecycleOwner){postsList ->
            postAdapter.modifyList(postsList)
        }


        postAdapter.onPostClockListener = {post ->

            val action = HomeFragmentDirections.actionHomeFragmentToQuestionFragment(post)
            findNavController().navigate(action)
        }
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(query: String): Boolean {
        postAdapter.filter(query)
        return true
    }
}