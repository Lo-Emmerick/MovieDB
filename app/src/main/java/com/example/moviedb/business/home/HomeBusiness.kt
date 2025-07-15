package com.example.moviedb.business.home

import com.example.moviedb.model.Genre
import com.example.moviedb.model.GenreMovie
import com.example.moviedb.model.MovieScreen

interface HomeBusiness {
    suspend fun getNowPlaying(genreList: List<Genre>?) : List<MovieScreen>
    suspend fun getUpcoming(genreList: List<Genre>?) : List<MovieScreen>
    suspend fun searchMovies(movieName: String, genreList: List<Genre>?) : List<MovieScreen>
    suspend fun getGenres() : GenreMovie
}