package com.example.themovietest.domain

import com.example.themovietest.core.Resources
import com.example.themovietest.data.model.ImageList

interface PhotoScreenRepo {

    suspend fun getLatestImages(): Resources<List<ImageList>>
}