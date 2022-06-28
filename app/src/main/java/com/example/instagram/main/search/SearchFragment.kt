package com.example.instagram.main.search

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.instagram.R
import com.example.instagram.data.PostDB
import com.example.instagram.data.UserDB
import com.example.instagram.databinding.FragmentSearchBinding
import com.example.instagram.main.MainActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson


class SearchFragment: Fragment() {

    private lateinit var binding: FragmentSearchBinding


    private val database = Firebase.database
    private val postRef = database.getReference("post")
    private var gson : Gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(inflater, container, false)

        //검색바 누르면 검색리스트 fragment로 전환
        binding.searchSearchbarIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, SearchListFragment()).commitAllowingStateLoss()
        }

        return binding.root
    }



    override fun onStart() {
        super.onStart()

        searchRecyclerView()
    }

    private fun searchRecyclerView() {

        val searchRVAdapter = SearchRVAdapter(getMyInfo(), getMyUid())
        binding.searchRv.adapter = searchRVAdapter
        binding.searchRv.layoutManager = GridLayoutManager(context, 3)

//
//        // 게시물 데이터 받아오기
//        postRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                if (snapshot.exists()) {
//                    for (postSnapshot in snapshot.children) {
//                        val getData = postSnapshot.getValue(PostDB::class.java)
//
//                        if (getData != null) {
//                            searchRVAdapter.addNewPost(getData)
//                        }
//
//                        Log.d("SUCCESS", getData.toString())
//                    }
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.d("FAIL", "데이터를 불러오지 못했습니다")
//            }
//        })


        searchRVAdapter.setMyItemClickListener(object : SearchRVAdapter.MyItemClickListener {
            override fun onShowSearchtab(post: PostDB) {
                showSearchTab(post)
            }
        })

    }

         // 내 정보를 가져오기 위한 함수
        private fun getMyInfo() : UserDB? {
            val userSP = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)

            val userJson = userSP.getString("myInfo", "")

            return gson.fromJson(userJson, UserDB::class.java)
        }

    private fun showSearchTab(post: PostDB) {  // 탐색탭

        val fragment=Fragment()
//
//        var bundle = Bundle()// 번들을 통해 값 전달
//
//        bundle.putString("userID",post.uid)
//        bundle.putString("picture", post.picture.toString())
//
//        fragment.arguments=bundle
        setFragment(fragment)

    }

    private fun getMyUid() : String? {  // 내 정보를 가져오기 위한 함수
        val userSP = requireActivity().getSharedPreferences("user", MODE_PRIVATE)

        return userSP.getString("myUid", "")
    }
private fun setFragment(fragment: Fragment){
    (context as MainActivity).supportFragmentManager.beginTransaction()
        .replace(R.id.main_frm,SearchTabFragment()).commitAllowingStateLoss()
}

}
