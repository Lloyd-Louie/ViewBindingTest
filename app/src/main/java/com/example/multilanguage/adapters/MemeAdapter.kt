package com.example.multilanguage.adapters

import android.content.ClipData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.multilanguage.R
import com.example.multilanguage.databinding.ItemMemeBinding
import com.example.multilanguage.model.MemeItem
import com.example.multilanguage.utils.loadUrl
import kotlin.properties.Delegates

class MemeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var memeList: List<MemeItem> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_meme, parent, false)
        return MemeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MemeViewHolder).bindItem(memeList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
       return memeList.size
    }

    inner class MemeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(meme: MemeItem) {
           val itemMemeBinding = ItemMemeBinding.bind(itemView)
            with(itemMemeBinding){
                memeIV.loadUrl(meme.link)
            }
        }
    }

    fun updateMemeList(memeList:List<MemeItem>){
        this.memeList = memeList
    }
}