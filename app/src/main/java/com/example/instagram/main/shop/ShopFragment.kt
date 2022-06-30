package com.example.instagram.main.shop

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.R
import com.example.instagram.databinding.FragmentShopBinding
import com.example.instagram.main.MainActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class ShopFragment : Fragment() {

    private lateinit var binding: FragmentShopBinding
    private val database = Firebase.database
    private val shopRef = database.getReference("shop")


    private var shopWishDatas = ArrayList<WishItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShopBinding.inflate(inflater, container, false)


        //shopping wish list dummy data
        shopWishDatas.apply {
            add(WishItem(0, R.drawable.shop_item3, "나일론 호보백", "nothingwritten"))
            add(WishItem(1, R.drawable.shop_item4, "penny bag", "alexanderwang"))
            add(WishItem(2, R.drawable.shop_item5, "Summer pants", "treemingbird"))
            add(WishItem(3, R.drawable.shop_item6, "Lap Skirt White", "treemingbird"))
        }



        binding.shopListRv.layoutManager =
            GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        val shopRVAdapter = ShopRVAdapter()
        binding.shopListRv.adapter = shopRVAdapter


        shopRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                /*shopRVAdapter.clearShop()*/

                for(shopSnapshot in snapshot.children){
                    val getShop = shopSnapshot.getValue(ShopItem::class.java)

                    if (getShop != null) {
                        shopRVAdapter.addShopItem(getShop)
                    }

                    Log.d("Success-shop", getShop.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("FAIL-SHOP", "No data")
            }
        } )

        shopRVAdapter.setMyItemClickListener(object : ShopRVAdapter.MyItemClickListener {
            override fun onItemClick(Shop: ShopItem) {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, ShopItemFragment().apply {
                        arguments = Bundle().apply {
                            val gson = Gson()
                            val shopJson = gson.toJson(Shop)
                            putString("shopItem", shopJson)
                        }
                    }).addToBackStack(null).commitAllowingStateLoss()
            }
        })


                binding.shopWishlistRv.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                val shopWishRVAdapter = ShopWishRVAdapter(shopWishDatas)
                binding.shopWishlistRv.adapter = shopWishRVAdapter


                binding.shopWishIv.setOnClickListener {
                    (context as MainActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, WishlistFragment()).addToBackStack(null)
                        .commitAllowingStateLoss()
                }

                /*binding.shopWishAllTv.setOnClickListener {
                    (context as MainActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, WishlistFragment())
                        .commitAllowingStateLoss()gig
                }*/


                val bottomSheet = ShopDialogFragment()

                binding.shopListIv.setOnClickListener {
                    bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
                }


                binding.shopMenuGuideTv.setOnClickListener {
                    (context as MainActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, GuideFragment()).addToBackStack(null)
                        .commitAllowingStateLoss()
                }

                binding.shopMenuVideoTv.setOnClickListener {
                    (context as MainActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, ShopVideoFragment()).addToBackStack(null)
                        .commitAllowingStateLoss()
                }

                binding.shopMenuEditorTv.setOnClickListener {
                    (context as MainActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, EditorFragment()).addToBackStack(null)
                        .commitAllowingStateLoss()
                }

                binding.shopMenuCollectionTv.setOnClickListener {
                    (context as MainActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, CollectionFragment()).addToBackStack(null)
                        .commitAllowingStateLoss()
                }

                return binding.root
            }
        }