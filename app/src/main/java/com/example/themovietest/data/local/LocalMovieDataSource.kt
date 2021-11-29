package com.example.themovietest.data.local

import com.example.themovietest.data.model.MovieEntity
import com.example.themovietest.data.model.MovieList
import com.example.themovietest.data.model.toMovieList

class LocalMovieDataSource(private val movieDao: MovieDao) {

    suspend fun getNowPlayingMovies(): MovieList {
       return movieDao.getAllMovies().filter { it.movie_type == "now_playing" }.toMovieList()
    }

    suspend fun getUpcomingMovies(): MovieList {
        return movieDao.getAllMovies().filter { it.movie_type == "upcoming" }.toMovieList()
    }

    suspend fun saveMovie(movie: MovieEntity){
        movieDao.saveMovie(movie)
    }
}