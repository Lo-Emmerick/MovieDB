package com.example.moviedb.repository.genres

import com.example.moviedb.model.GenreMovie
import com.example.moviedb.network.Endpoint

class GenresRepositoryImpl(
    private val api : Endpoint
) : GenresRepository {

    override suspend fun getGenres(): GenreMovie {
        return api.getGenres()
    }
}