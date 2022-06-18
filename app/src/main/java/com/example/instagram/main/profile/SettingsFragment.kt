package com.example.instagram.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.databinding.FragmentListBinding
import com.example.instagram.databinding.FragmentSettingsBinding
import com.example.instagram.login.LoginActivity
import com.example.instagram.main.MainActivity

class SettingsFragment: Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSettingsBinding.inflate(inflater, container, false)

<<<<<<< HEAD
        binding.sttsBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ProfileFragment()).commitAllowingStateLoss()
        }
=======
        binding.sttsBackIv.setOnClickListener {  }
>>>>>>> 034338c3bbf7aae0f5b9e8bcc09467cac36377f4

        binding.sttsLogoutTv.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
        }

        return binding.root
    }
}