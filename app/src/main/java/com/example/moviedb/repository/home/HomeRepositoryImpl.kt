package com.example.moviedb.repository.home

import com.example.moviedb.model.MovieList
import com.example.moviedb.network.Endpoint
import com.example.moviedb.network.RetrofitInstance

class HomeRepositoryImpl (
    private val api : Endpoint
) : HomeRepository {
    override suspend fun getNowPlaying(): MovieList {
        return api.getNowPlaying()
    }

    override suspend fun getUpcoming(): MovieList {
        return api.getUpcoming()
    }

    override suspend fun searchMovies(movieName: String): MovieList {
        return api.searchMovies(movieName)
    }
}