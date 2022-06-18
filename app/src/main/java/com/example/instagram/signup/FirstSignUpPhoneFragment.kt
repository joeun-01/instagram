package com.example.instagram.signup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.instagram.databinding.FragmentFirstSignupPhoneBinding

class FirstSignUpPhoneFragment : Fragment() {

    lateinit var binding: FragmentFirstSignupPhoneBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFirstSignupPhoneBinding.inflate(inflater, container, false)

        binding.firstSignupPhoneNextTv.setOnClickListener {
            startActivity(Intent(requireActivity(), FirstSignUpStep2Activity::class.java))
        }

        return binding.root
    }
}