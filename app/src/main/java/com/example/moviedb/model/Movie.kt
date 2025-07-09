package com.example.moviedb.model

@kotlinx.serialization.Serializable

data class Movie(
    val id: Int,
    val title: String,
    val genre_ids: List<Int>,
    val release_date: String,
    val popularity: Double,
    val poster_path: String,
);
