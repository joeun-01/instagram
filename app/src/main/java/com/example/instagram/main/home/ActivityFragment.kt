package com.example.instagram.main.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.R
import com.example.instagram.data.LikedPostDB
import com.example.instagram.data.PostDB
import com.example.instagram.data.StoryDB
import com.example.instagram.databinding.FragmentActivityBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ActivityFragment : Fragment() {

    private lateinit var binding : FragmentActivityBinding

    private val database = Firebase.database
    private val likeRef = database.getReference("likedPost")
    private val postRef = database.getReference("post")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentActivityBinding.inflate(inflater, container, false)

        binding.activityBackBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment()).commit()
        }

        val likeRVAdapter = LikeRVAdapter()
        binding.activityPostLikeRv.adapter = likeRVAdapter
        binding.activityPostLikeRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // 좋아요한 게시물 데이터 받아오기
        likeRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                likeRVAdapter.clearLike()

                if (snapshot.exists()){
                    for (likeSnapshot in snapshot.children){
                        val getData = likeSnapshot.getValue(LikedPostDB::class.java)

                        if (getData != null) {
                            if(getData.uid != getMyUid()) {  // 내가 누른 좋아요가 아니어야 함
                                // 내가 올린 게시물인지 확인
                                var post : PostDB
                                postRef.child(getData.postIdx.toString()).get().addOnSuccessListener {
                                    post = it.getValue(PostDB::class.java)!!

                                    if(post.uid == getMyUid()) {
                                        // 내가 올린 게시물이 맞으면 추가
                                        likeRVAdapter.addNewLike(getData)
                                        Log.d("OMG", getData.toString())
                                    }

                                }
                                .addOnFailureListener {
                                    Log.d("FAIL-POST", "게시물 데이터를 받아오지 못했습니다")
                                }

                            }
                        }

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
}