package com.example.moviedb.model

data class MovieScreen(
    val id: Int,
    val title: String,
    val genre: String,
    val release_date: String,
    val vote_average: Double,
    val poster_path: String,
);
