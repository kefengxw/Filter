package com.filter.android.model.repository

data class DisplayData(
    var total: Int = 0,
    var item: List<Item>? = null
)

data class Item( //each artist information
    val name: String = "",
    val imagesUrl: String = ""
)