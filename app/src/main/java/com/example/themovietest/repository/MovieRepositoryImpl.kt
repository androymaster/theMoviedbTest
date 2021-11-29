package com.example.themovietest.repository

import com.example.themovietest.data.local.LocalMovieDataSource
import com.example.themovietest.data.model.MovieList
import com.example.themovietest.data.model.toMovieEntity
import com.example.themovietest.data.remote.RemoteMovieDataSource

class MovieRepositoryImpl(
    private val dataSourceRemote: RemoteMovieDataSource,
    private val dataSourceLocal: LocalMovieDataSource
) : MovieRepository {

    override suspend fun getNowPlayingMovies(): MovieList {
        dataSourceRemote.getNowPlayingMovies().results.forEach { movie ->
            dataSourceLocal.saveMovie(movie.toMovieEntity("now_playing"))
        }
        return dataSourceLocal.getNowPlayingMovies()
    }

    override suspend fun getUpcomingMovies(): MovieList {
        dataSourceRemote.getUpcomingMovies().results.forEach{ movie ->
            dataSourceLocal.saveMovie(movie.toMovieEntity("upcoming"))
        }
        return dataSourceLocal.getUpcomingMovies()
    }
}