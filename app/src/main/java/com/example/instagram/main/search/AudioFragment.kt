package com.example.instagram.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.instagram.databinding.FragmentSearchAudioBinding

class AudioFragment: Fragment() {
    lateinit var binding: FragmentSearchAudioBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSearchAudioBinding.inflate(inflater,container,false)
        return binding.root
    }
}