package com.example.themovietest.repository

import com.example.themovietest.core.InternetCheck
import com.example.themovietest.data.local.LocalMovieDataSource
import com.example.themovietest.data.model.MovieList
import com.example.themovietest.data.model.toMovieEntity
import com.example.themovietest.data.remote.RemoteMovieDataSource

class MovieRepositoryImpl(
    private val dataSourceRemote: RemoteMovieDataSource,
    private val dataSourceLocal: LocalMovieDataSource
) : MovieRepository {

    override suspend fun getNowPlayingMovies(): MovieList {
        return if (InternetCheck.isNetworkAvailable()) {
            dataSourceRemote.getNowPlayingMovies().results.forEach { movie ->
                dataSourceLocal.saveMovie(movie.toMovieEntity("now_playing"))
            }
            dataSourceLocal.getNowPlayingMovies()
        } else {
            dataSourceLocal.getNowPlayingMovies()
        }
    }

    override suspend fun getUpcomingMovies(): MovieList {
        return if (InternetCheck.isNetworkAvailable()) {
            dataSourceRemote.getUpcomingMovies().results.forEach { movie ->
                dataSourceLocal.saveMovie(movie.toMovieEntity("upcoming"))
            }
            dataSourceLocal.getUpcomingMovies()
        } else {
            dataSourceLocal.getUpcomingMovies()
        }
    }
}