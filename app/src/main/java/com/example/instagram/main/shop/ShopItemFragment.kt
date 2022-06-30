package com.example.instagram.main.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.instagram.databinding.FragmentShopItemBinding
import com.example.instagram.main.MainActivity

class ShopItemFragment : Fragment() {

    private lateinit var binding: FragmentShopItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShopItemBinding.inflate(inflater, container, false)

       with(binding){
            itemContentsEl.setOnExpandListener {
                if(it){
                    Toast.makeText(context, "expanded", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(context, "collapse", Toast.LENGTH_SHORT).show()
                }
            }

            itemContentsEl.parentLayout.setOnClickListener{itemContentsEl.toggleLayout()}

            itemContents2El.parentLayout.setOnClickListener { itemContents2El.toggleLayout() }
        }

        binding.backIb.setOnClickListener {
            (context as MainActivity).onBackPressed()
        }





        return binding.root
    }
}