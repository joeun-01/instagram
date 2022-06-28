package com.example.instagram.signup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.instagram.databinding.FragmentSignupPhoneBinding

class SignUpPhoneFragment : Fragment() {

    lateinit var binding: FragmentSignupPhoneBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSignupPhoneBinding.inflate(inflater, container, false)

        binding.firstSignupPhoneNextTv.setOnClickListener {
            startActivity(Intent(requireActivity(), SignUpNameActivity::class.java))
        }

        return binding.root
    }
}