package com.example.instagram.main.profile

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.databinding.FragmentListBinding
import com.example.instagram.databinding.FragmentSettingsBinding
import com.example.instagram.login.LoginActivity
import com.example.instagram.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SettingsFragment: Fragment() {

    private lateinit var auth: FirebaseAuth

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        auth = Firebase.auth


        binding.sttsBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ProfileFragment()).commitAllowingStateLoss()
        }

        binding.sttsLogoutTv.setOnClickListener {
            // 로그아웃하면 저장한 uid를 지워줌
            val userSP = requireActivity().getSharedPreferences("user", MODE_PRIVATE)
            val userEditor = userSP.edit()

            userEditor.remove("myUid")
            userEditor.remove("myInfo")
            userEditor.apply()

            auth.signOut()
            Log.d("SUCCESS-MYID", auth.currentUser.toString())

            startActivity(Intent(activity, LoginActivity::class.java))
        }

        return binding.root
    }
}