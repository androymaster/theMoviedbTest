package com.example.themovietest.repository

import com.example.themovietest.data.model.MovieList
import com.example.themovietest.data.remote.MovieDataSource

class MovieRepositoryImpl(private val dataSource:MovieDataSource) : MovieRepository {

    override suspend fun getNowPlayingMovies(): MovieList = dataSource.getNowPlayingMovies()

    override suspend fun getUpcomingMovies(): MovieList = dataSource.getUpcomingMovies()

}