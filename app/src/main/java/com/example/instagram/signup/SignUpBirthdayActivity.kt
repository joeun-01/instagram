package com.example.instagram.signup

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.R
import com.example.instagram.databinding.ActivitySignupBirthdayBinding

class SignUpBirthdayActivity : AppCompatActivity() {

    lateinit var binding : ActivitySignupBirthdayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBirthdayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 생일 저장 후 다음 단계로
        binding.signupBirthdayDateDp.init(2022, 5, 30, object : DatePicker.OnDateChangedListener{
            @SuppressLint("SetTextI18n")
            override fun onDateChanged(view: DatePicker?, year: Int, month: Int, day: Int) {
                binding.signupBirthdayEt.setText(year.toString() + "년 " + (month + 1).toString() + "월 " + day.toString() + "일")
                binding.signupBirthdayNextTv.setBackgroundResource(R.drawable.blue_round_stroke)
            }

        })

        binding.signupBirthdayNextTv.setOnClickListener {
            startActivity(Intent(this, SignUpTermActivity::class.java))
        }

    }
}