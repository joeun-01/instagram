package com.example.instagram.signup

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.instagram.main.home.HomeFragment

class SignUpVPAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            // 여기에 들어간 Fragment는 나중에 만들어서 수정해주면 됩니닷
            0 -> FirstSignUpPhoneFragment()
            else -> FirstSignUpEmailFragment()
        }
    }
}