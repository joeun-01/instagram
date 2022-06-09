package com.example.instagram.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.databinding.FragmentSearchlistBinding
import com.example.instagram.main.MainActivity

class SearchListFragment : Fragment(){

    private lateinit var binding: FragmentSearchlistBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = FragmentSearchlistBinding.inflate(inflater, container, false)

        //검색 취소
        binding.searchlistCloseTv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, SearchFragment()).commitAllowingStateLoss()
        }

        return binding.root

    }
}