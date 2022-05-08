package com.example.todoapp_hw16.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.todoapp_hw16.ui.doing.DoingFragment
import com.example.todoapp_hw16.ui.done.DoneFragment
import com.example.todoapp_hw16.ui.todo.TodoFragment

class PagerAdapter(fragmentManager: FragmentManager , lifecycle: Lifecycle):
    FragmentStateAdapter(fragmentManager,lifecycle) {


    override fun getItemCount(): Int {
       return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TodoFragment()
            1 -> DoingFragment()
            2 -> DoneFragment()
            else -> Fragment()
        }
    }

}