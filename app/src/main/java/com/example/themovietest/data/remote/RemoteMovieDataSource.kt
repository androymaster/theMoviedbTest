package com.example.themovietest.data.remote

import com.example.themovietest.application.AppConstants
import com.example.themovietest.data.model.MovieList
import com.example.themovietest.repository.WebService

class RemoteMovieDataSource(private val webService: WebService) {

    suspend fun getNowPlayingMovies(): MovieList {
        return webService.getNowplayingMovies(AppConstants.API_KEY)
    }

    suspend fun getUpcomingMovies(): MovieList {
        return webService.getUpcomingMovies(AppConstants.API_KEY)
    }
}