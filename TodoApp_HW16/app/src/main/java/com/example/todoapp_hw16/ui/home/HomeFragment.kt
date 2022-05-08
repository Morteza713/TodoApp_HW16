package com.example.todoapp_hw16.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.todoapp_hw16.R
import com.example.todoapp_hw16.databinding.HomeFragmentBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment():Fragment(R.layout.home_fragment) {

    lateinit var binding: HomeFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = HomeFragmentBinding.bind(view)

        findNavController().enableOnBackPressed(false)
        binding.vpHome.adapter = PagerAdapter(childFragmentManager,lifecycle)
        TabLayoutMediator(binding.tbHome,binding.vpHome){tab,pos->
            when(pos){
                0 -> tab.text = "Todo"
                1 -> tab.text = "Doing"
                2 -> tab.text = "Done"
            }
        }.attach()

        binding.btnAddTodo.setOnClickListener {
            view.findNavController().navigate(R.id.taskFragment)
        }
    }
}