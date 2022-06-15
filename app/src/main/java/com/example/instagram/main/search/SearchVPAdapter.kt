package com.example.instagram.main.search

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SearchVPAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int =5

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> BestFragment()
            1-> AccountFragment()
            2-> AudioFragment()
            3-> TagFragment()
            else-> PlaceFragment()
        }
    }
}