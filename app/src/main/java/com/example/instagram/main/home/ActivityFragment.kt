package com.example.instagram.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.databinding.FragmentActivityBinding

class ActivityFragment : Fragment() {

    private lateinit var binding : FragmentActivityBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentActivityBinding.inflate(inflater, container, false)

        binding.activityBackBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment()).commit()
        }



        return binding.root
    }
}