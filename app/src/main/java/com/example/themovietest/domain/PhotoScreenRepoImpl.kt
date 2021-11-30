package com.example.themovietest.domain

import com.example.themovietest.core.Resources
import com.example.themovietest.data.model.ImageList
import com.example.themovietest.data.remote.PhotoScreenDataSource

class PhotoScreenRepoImpl(private val dataSource: PhotoScreenDataSource): PhotoScreenRepo {

    override suspend fun getLatestImages(): Resources<List<ImageList>>  = dataSource.getLatestImages()
}