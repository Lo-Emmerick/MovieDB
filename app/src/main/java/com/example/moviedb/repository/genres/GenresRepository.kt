package com.example.moviedb.repository.genres

import com.example.moviedb.model.GenreMovie

interface GenresRepository {
    suspend fun getGenres() : GenreMovie
}