package com.example.moviedb.model

@kotlinx.serialization.Serializable

data class MovieAPI(
    val id: Int,
    val title: String,
    val genre_ids: List<Int>,
    val release_date: String,
    val vote_average: Double,
    val poster_path: String,
);
