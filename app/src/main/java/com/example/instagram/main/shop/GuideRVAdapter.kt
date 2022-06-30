package com.example.instagram.main.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.databinding.ItemGuideBinding

class GuideRVAdapter(private val guideList: ArrayList<Guide>): RecyclerView.Adapter<GuideRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick()
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemGuideBinding =
            ItemGuideBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GuideRVAdapter.ViewHolder, position: Int) {
        holder.bind(guideList[position])

    }

    override fun getItemCount(): Int = guideList.size

    inner class ViewHolder(private val binding: ItemGuideBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(guide: Guide) {
            binding.guideProfileIv.setImageResource(guide.guideImg!!)
            binding.guideIv.setImageResource(guide.guideImg!!)
            binding.guideTotalTv.text = guide.total.toString()
            binding.guideBrandTv.text = guide.shopId.toString()
            binding.guideNameTv.text = guide.name.toString()


            /*binding.guideCv.setOnClickListener {
                mItemClickListener.onItemClick()
            }*/

        }
    }
}