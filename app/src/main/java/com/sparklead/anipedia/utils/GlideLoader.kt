package com.sparklead.anipedia.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.sparklead.anipedia.R
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject

class GlideLoader (private val context:Context) {

    fun loadAnimePicture(image: Any, imageView: ImageView) {
        try {
            Glide
                .with(context)
                .load(image)
                .placeholder(R.drawable.anipedia_icon)
                .into(imageView)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}