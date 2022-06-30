package com.example.instagram.main.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.instagram.R
import com.example.instagram.databinding.FragmentCartBinding
import com.example.instagram.databinding.FragmentShopBinding
import com.example.instagram.main.MainActivity
import com.example.instagram.main.home.HomeFragment

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        binding.backIb.setOnClickListener {
            (context as MainActivity).onBackPressed()
        }


        return binding.root
    }
}