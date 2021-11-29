package com.example.themovietest.repository

import com.example.themovietest.data.model.MovieList

interface MovieRepository {

   suspend fun getNowPlayingMovies(): MovieList
   suspend fun getUpcomingMovies(): MovieList

}