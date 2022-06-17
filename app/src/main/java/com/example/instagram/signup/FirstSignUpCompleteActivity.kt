package com.example.instagram.signup

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.data.User
import com.example.instagram.databinding.ActivityFirstSignupCompleteBinding
import com.example.instagram.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson


class FirstSignUpCompleteActivity : AppCompatActivity() {

    lateinit var binding : ActivityFirstSignupCompleteBinding

    private val gson : Gson = Gson()
    private var auth : FirebaseAuth? = null
    private lateinit var mDatabase : DatabaseReference

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFirstSignupCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        mDatabase = FirebaseDatabase.getInstance().reference

        // 유저 정보 가져오기
        val userSP = getSharedPreferences("user", MODE_PRIVATE)
        val userJson = userSP.getString("userInfo", "")

        val user = gson.fromJson(userJson, User::class.java)

        binding.firstSignupCompleteTitleTv.text = user.ID + "님으로 가입하시겠어요?"

        // 로그인 화면 연결
        binding.firstSignupCompleteLoginTv.setOnClickListener {
            startLoginActivity()
        }

        binding.firstSignupCompleteNextTv.setOnClickListener {
            createAccount(user.email, user.password)
            putIntoDatabase(user)  // 이 따 얘를 create account로 넘겨주자
//            readUser(this)
            startLoginActivity()
        }

    }

    private fun createAccount(email: String, password: String) {
        auth?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener(this) {
            task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "계정 생성 완료", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "계정 생성 실패", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun putIntoDatabase(user : User) {
        mDatabase.child("user").child("1").setValue(user)
    }

    private fun readUser(context : Context) {
        mDatabase.child("users").child("1").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if (dataSnapshot.getValue(User::class.java) != null) {
                    val post = dataSnapshot.getValue(User::class.java)
                    Log.w("FireBaseData", "getData" + post.toString())
                } else {
                    Toast.makeText(context, "데이터 없음...", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException())
            }
        })
    }

    private fun startLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}