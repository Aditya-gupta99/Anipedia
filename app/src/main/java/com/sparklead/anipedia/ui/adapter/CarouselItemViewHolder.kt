package com.sparklead.anipedia.ui.adapter

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.sparklead.anipedia.R
import com.sparklead.anipedia.utils.CarouselItem

internal class CarouselItemViewHolder(itemView: View, listener: CarouselAdapter.CarouselItemListener) :
    RecyclerView.ViewHolder(itemView) {
    private val imageView: ImageView
    private val tv : TextView
    private val listener: CarouselAdapter.CarouselItemListener

    init {
        imageView = itemView.findViewById(R.id.iv_anime)
        this.listener = listener
        tv = itemView.findViewById(R.id.tv_anime_title)
    }

    fun bind(item: CarouselItem) {
        tv.text = item.getTitle()
        Glide
            .with(imageView.context)
            .asBitmap()
            .load(item.getImageUrl())
            .into(object : CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Log.e("SET DRAWABLE BITMAP", resource.toString())
                    imageView.setImageBitmap(resource)
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
        itemView.setOnClickListener {
            listener.onItemClicked(
                item,
                adapterPosition
            )
        }
    }
}