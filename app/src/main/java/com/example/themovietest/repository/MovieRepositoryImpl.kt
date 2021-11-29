package com.example.themovietest.repository

import com.example.themovietest.data.model.MovieList
import com.example.themovietest.data.remote.RemoteMovieDataSource

class MovieRepositoryImpl(private val dataSourceRemote:RemoteMovieDataSource) : MovieRepository {

    override suspend fun getNowPlayingMovies(): MovieList = dataSourceRemote.getNowPlayingMovies()

    override suspend fun getUpcomingMovies(): MovieList = dataSourceRemote.getUpcomingMovies()

}