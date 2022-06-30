package com.example.instagram.main.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.instagram.R
import com.example.instagram.data.PostDB
import com.example.instagram.data.SearchDB
import com.example.instagram.data.StoryDB
import com.example.instagram.data.UserDB
import com.example.instagram.databinding.FragmentSearchBinding
import com.example.instagram.main.MainActivity
import com.example.instagram.main.home.StoryActivity
import com.example.instagram.main.home.StoryRVAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson


class SearchFragment: Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private var gson : Gson = Gson()

    // 파이어베이스
    private val database = Firebase.database
    private val searchRef = database.getReference("search")



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

        initRecyclerView()
    }

    private fun initRecyclerView() {

        val searchRVAdapter = SearchRVAdapter(getMyInfo(), getMyUid())
        binding.searchRv.adapter = searchRVAdapter
        binding.searchRv.layoutManager =
            GridLayoutManager(context, 3)

        searchRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){
                    for (searchSnapshot in snapshot.children){
                        val getData = searchSnapshot.getValue(SearchDB::class.java)

                        if (getData != null) {
                            searchRVAdapter.addNewSearch(getData)
                        }

                        Log.d("SUCCESS", getData.toString())
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("FAIL", "데이터를 불러오지 못했습니다")
            }
        })

        searchRVAdapter.setMyItemClickListener(object : SearchRVAdapter.MyItemClickListener {
            override fun onShowSearchtab(search: SearchDB) {
                showSearchtab(search)
            }
        })

    }
    private fun showSearchtab(search: SearchDB) {  // 탐색탭


        val searchtabFragment=SearchTabFragment()
//
//        var bundle=Bundle()
//        bundle.putString("userId",search.uid)
//        bundle.putString("picture",search.picture.toString())
//        bundle.putString("text",search.text)
////
//        searchtabFragment.arguments=bundle
        setFragment(searchtabFragment)


    }

    private fun setFragment(fragment: Fragment){
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm,SearchTabFragment()).commitAllowingStateLoss()
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
}
