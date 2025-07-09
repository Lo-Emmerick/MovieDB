package com.example.moviedb.model

data class DetailsMovie(
    val poster_path: String,
    val title: String,
    val backdrop_path : String,
    val vote_average: Double,
    val runtime: Int,
    val genres: List<GenreDetails>,
    val overview: String
)

data class GenreDetails (
    val id: Int,
    val name: String
)