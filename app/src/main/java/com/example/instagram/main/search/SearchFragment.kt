package com.example.instagram.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.instagram.R
import com.example.instagram.databinding.FragmentSearchBinding
import com.example.instagram.main.MainActivity


class SearchFragment: Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private var searchDatas = ArrayList<SearchItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding.searchSearchbarIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, SearchListFragment()).commitAllowingStateLoss()
        }


        //더미데이터
        searchDatas.apply {
            add(SearchItem(0, R.drawable.search_img1))
            add(SearchItem(0, R.drawable.search_img2))
            add(SearchItem(0, R.drawable.search_img3))
            add(SearchItem(0, R.drawable.search_img5))
            add(SearchItem(0, R.drawable.search_img4))
            add(SearchItem(0, R.drawable.search_img6))
            add(SearchItem(0, R.drawable.search_img7))
            add(SearchItem(0, R.drawable.search_img8))
            add(SearchItem(0, R.drawable.search_img9))
            add(SearchItem(0, R.drawable.search_img10))
            add(SearchItem(0, R.drawable.search_img11))
            add(SearchItem(0, R.drawable.search_img12))
            add(SearchItem(0, R.drawable.search_img13))
            add(SearchItem(0, R.drawable.search_img14))
            add(SearchItem(0, R.drawable.search_img15))
        }


        val searchRVAdapter = SearchRVAdapter(searchDatas)
        binding.searchRv.adapter = searchRVAdapter
        binding.searchRv.layoutManager =
            GridLayoutManager(context, 3)

        return binding.root
    }
}
