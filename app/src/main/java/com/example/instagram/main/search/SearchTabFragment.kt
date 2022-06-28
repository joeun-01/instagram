package com.example.instagram.main.search

import android.content.Context
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.provider.Settings.Secure.getString
import android.provider.Settings.System.getString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.R
import com.example.instagram.data.PostDB
import com.example.instagram.data.UserDB
import com.example.instagram.databinding.FragmentSearchtabBinding
import com.example.instagram.main.MainActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class SearchTabFragment:Fragment() {

    private lateinit var binding: FragmentSearchtabBinding
    private val gson: Gson = Gson()


    private val database = Firebase.database
    private val postRef = database.getReference("post")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchtabBinding.inflate(inflater, container, false)


        //뒤로가기 누르면 검색 fragment로 전환
        binding.searchtabBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, SearchFragment()).commitAllowingStateLoss()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        searchRecyclerView()
    }

    private fun searchRecyclerView() {
        val searchTabRVAdapter = SearchTabRVAdapter(getMyInfo(), getMyUid())
        binding.searchtabRv.adapter = searchTabRVAdapter
        binding.searchtabRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        postRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    for (postSnapshot in snapshot.children) {
                        val getData = postSnapshot.getValue(PostDB::class.java)

                        if (getData != null) {
                            searchTabRVAdapter.addNewSearchtab(getData)
                        }

                        Log.d("SUCCESS", getData.toString())
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("FAIL", "데이터를 불러오지 못했습니다")
            }
        })
        }

    private fun getMyInfo(): UserDB? {
        val userSP = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)

        val userJson = userSP.getString("myInfo", "")

        return gson.fromJson(userJson, UserDB::class.java)
    }

    private fun getMyUid() : String? {
        val userSP = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)

        return userSP.getString("myUid", "")
    }

}

