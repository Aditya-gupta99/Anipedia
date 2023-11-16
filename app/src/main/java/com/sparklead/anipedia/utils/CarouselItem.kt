package com.sparklead.anipedia.utils

class CarouselItem(private val url: String = "",private val title : String) {

    fun getImageUrl() : String = url

    fun getTitle() : String = title
}