package com.example.instagram.main.search


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.databinding.FragmentSearchrstBinding
import com.example.instagram.main.MainActivity
import com.google.android.material.tabs.TabLayoutMediator


class SearchRstFragment : Fragment(){

    private lateinit var binding: FragmentSearchrstBinding

    private val information = arrayListOf("인기", "계정", "오디오","태그","장소")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = FragmentSearchrstBinding.inflate(inflater, container, false)

        //뒤로가기
        binding.searchrstBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, SearchListFragment()).commitAllowingStateLoss()
        }

        val searchVPAdapter = SearchVPAdapter(this)
        binding.searchrstContentVp.adapter = searchVPAdapter

        //tabLayout과 viewPager를 연결
        TabLayoutMediator(binding.searchrstTabTb, binding.searchrstContentVp) { tab, position ->
            tab.text = information[position]
        }.attach()


        return binding.root

    }
}