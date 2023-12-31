package com.anangkur.wallpaper.data.model

data class Wallpaper(
    val id: String,
    val title: String,
    val imageUrl: String,
    val thumbnailUrl: String,
    val creator: String,
    val isSaved: Boolean
)
