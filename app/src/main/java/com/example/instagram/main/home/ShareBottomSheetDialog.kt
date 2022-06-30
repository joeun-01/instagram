package com.example.instagram.main.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.R
import com.example.instagram.data.UserDB
import com.example.instagram.databinding.SharePostBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class ShareBottomSheetDialog : BottomSheetDialogFragment() {
    lateinit var binding : SharePostBottomSheetDialogBinding

    private val gson : Gson = Gson()

    // 유저 파이어베이스
    private val database = Firebase.database
    private val userRef = database.getReference("user")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SharePostBottomSheetDialogBinding.inflate(inflater, container, false)

        // 유저 리사이클러뷰 연결
        val shareRVAdapter = ShareRVAdapter(getMyInfo()!!)
        binding.sharePostUserRv.adapter = shareRVAdapter
        binding.sharePostUserRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // 유저 데이터 받아오기
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                shareRVAdapter.clearInfo()

                if (snapshot.exists()){
                    for (postSnapshot in snapshot.children){
                        val getData = postSnapshot.getValue(UserDB::class.java)

                        if (getData != null) {
                            if(getData.ID == getMyInfo()!!.ID) {
                                // 내 정보는 맨 앞에
                                shareRVAdapter.addMyInfo(getData)
                            }
                            else {
                                // 다른 사람 정보는 상관 X
                                shareRVAdapter.addUserInfo(getData)
                            }
                        }

                        Log.d("SUCCESS", getData.toString())
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("FAIL", "데이터를 불러오지 못했습니다")
            }
        })

        return binding.root
    }

    private fun getMyUid() : String? {  // 내 정보를 가져오기 위한 함수
        val userSP = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)

        return userSP.getString("myUid", "")
    }

    private fun getMyInfo() : UserDB? {  // 내 정보를 가져오기 위한 함수
        val userSP = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)

        val userJson = userSP.getString("myInfo", "")

        return gson.fromJson(userJson, UserDB::class.java)
    }

    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme
}