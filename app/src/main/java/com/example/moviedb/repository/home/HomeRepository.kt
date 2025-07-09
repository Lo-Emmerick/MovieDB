package com.example.moviedb.repository.home

import com.example.moviedb.model.MovieList

interface HomeRepository {
    suspend fun getNowPlaying() : MovieList
    suspend fun getUpcoming() : MovieList
    suspend fun searchMovies(movieName: String) : MovieList
}