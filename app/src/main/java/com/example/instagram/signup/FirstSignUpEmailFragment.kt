package com.example.instagram.signup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.data.UserDB
import com.example.instagram.databinding.FragmentFirstSignupEmailBinding
import com.google.gson.Gson

class FirstSignUpEmailFragment : Fragment() {

    lateinit var binding: FragmentFirstSignupEmailBinding

    private var gson : Gson = Gson()
    private var user = UserDB("", "", "", "", R.drawable.basic_profile)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFirstSignupEmailBinding.inflate(inflater, container, false)

        binding.firstSignupEmailNextTv.setOnClickListener {
            val email = binding.firstSignupEmailEt.text.toString()

            if(email.isNotEmpty()) {
                val userSP = requireActivity().getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
                val userEditor = userSP.edit()

                user.email = email

                val userJson = gson.toJson(user)
                userEditor.putString("userInfo", userJson)

                userEditor.apply()

                startActivity(Intent(requireActivity(), FirstSignUpStep2Activity::class.java))
            }
            else {
                Toast.makeText(requireActivity(), "이메일을 입력해야합니다.", Toast.LENGTH_SHORT).show()
            }
        }


        return binding.root
    }
}