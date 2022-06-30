package com.example.instagram.signup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.instagram.R
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

        // EditText가 채워지면 다음 버튼 색 변경
        binding.firstSignupPhoneEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.firstSignupPhoneEt.hint = "입력 시작"
                // 입력이 시작되기 전에 작동
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // 입력이 시작되면 작동
                binding.firstSignupPhoneNextTv.setBackgroundResource(R.drawable.blue_round_stroke)

                if(p0.isNullOrBlank()) {
                    binding.firstSignupPhoneNextTv.setBackgroundResource(R.drawable.lightblue_round_stroke)
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                // 입력이 끝난 후에 작동
                binding.firstSignupPhoneEt.hint = "입력 끝"

            }
        })

        return binding.root
    }
}