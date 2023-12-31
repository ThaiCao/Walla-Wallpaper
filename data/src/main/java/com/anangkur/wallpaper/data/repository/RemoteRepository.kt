package com.anangkur.wallpaper.data.repository

import com.anangkur.wallpaper.data.model.Collection
import com.anangkur.wallpaper.data.model.Wallpaper

interface RemoteRepository {
    suspend fun fetchWallpaper(clientId: String): List<Wallpaper>
    suspend fun fetchCollection(clientId: String): List<Collection>
    suspend fun fetchCollections(clientId: String, page: Int, perPage: Int): List<Collection>
    suspend fun fetchCollectionPhotos(clientId: String, collectionId: String): List<Wallpaper>
    suspend fun fetchSearchPhotosByColor(clientId: String, color: String): List<Wallpaper>
    suspend fun fetchSearchPhotosByQuery(clientId: String, query: String): List<Wallpaper>
}