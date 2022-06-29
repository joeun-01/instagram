package com.example.instagram.main.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.databinding.ItemCollectionBinding
import com.example.instagram.databinding.ItemEditorBinding
import kotlin.collections.Collection

class EditorRVAdapter (private val editorList: ArrayList<Editor>): RecyclerView.Adapter<EditorRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick()
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemEditorBinding =
            ItemEditorBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EditorRVAdapter.ViewHolder, position: Int) {
        holder.bind(editorList[position])

    }

    override fun getItemCount(): Int = editorList.size

    inner class ViewHolder(private val binding: ItemEditorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(editor: Editor) {
           binding.itemEditorBrandTv.text = editor.shopId.toString()
            binding.itemEditorIv.setImageResource(editor.itemImg!!)
            binding.itemEditorNameTv.text = editor.itemName.toString()

            /*binding.guideCv.setOnClickListener {
                mItemClickListener.onItemClick()
            }*/

        }
    }
}