package com.example.instagram.main.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.R
import com.example.instagram.databinding.FragmentCollectionBinding
import com.example.instagram.main.MainActivity


class CollectionFragment : Fragment() {

    private lateinit var binding: FragmentCollectionBinding
    private var collections = ArrayList<Collection>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCollectionBinding.inflate(inflater, container, false)


        binding.collectionRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val collectionRVAdapter = CollectionRVAdapter(collections)
        binding.collectionRv.adapter = collectionRVAdapter

        /*guideRVAdapter.setMyItemClickListener(object : GuideRVAdapter.MyItemClickListener {
            override fun onItemClick(){
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, ShopItemFragment()).commitAllowingStateLoss()
            }
        })*/

        binding.collectionWishIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, WishlistFragment()).addToBackStack(null).commitAllowingStateLoss()
        }

        collections.apply {
            add(Collection(R.drawable.profile_ex1, "seveteen.official", "Bella Vita Rosa", 13, R.drawable.collection))

        }

        binding.backIb.setOnClickListener {
            (context as MainActivity).onBackPressed()
        }

        return binding.root
    }
}