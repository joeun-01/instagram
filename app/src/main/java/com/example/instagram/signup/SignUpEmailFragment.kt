package com.example.instagram.signup

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.data.UserDB
import com.example.instagram.databinding.FragmentSignupEmailBinding
import com.google.gson.Gson

class SignUpEmailFragment : Fragment() {

    lateinit var binding: FragmentSignupEmailBinding

    private var gson : Gson = Gson()
    private var user = UserDB("", "", "", "", R.drawable.basic_profile)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSignupEmailBinding.inflate(inflater, container, false)

        // EditText가 채워지면 다음 버튼 색 변경
        binding.firstSignupEmailEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.firstSignupEmailEt.hint = "입력 시작"
                // 입력이 시작되기 전에 작동
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // 입력이 시작되면 작동
                binding.firstSignupEmailNextTv.setBackgroundResource(R.drawable.blue_round_stroke)
            }

            override fun afterTextChanged(p0: Editable?) {
                // 입력이 끝난 후에 작동
                binding.firstSignupEmailEt.hint = "입력 끝"

            }
        })

        // 이메일 저장 후 다음 단계로
        binding.firstSignupEmailNextTv.setOnClickListener {
            val email = binding.firstSignupEmailEt.text.toString()

            if(email.isNotEmpty()) {
                val userSP = requireActivity().getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
                val userEditor = userSP.edit()

                user.email = email

                val userJson = gson.toJson(user)
                userEditor.putString("userInfo", userJson)

                userEditor.apply()

                startActivity(Intent(requireActivity(), SignUpNameActivity::class.java))
            }
            else {
                Toast.makeText(requireActivity(), "이메일을 입력해야합니다.", Toast.LENGTH_SHORT).show()
            }
        }


        return binding.root
    }
}