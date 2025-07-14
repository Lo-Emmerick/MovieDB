package com.example.moviedb.model

class GenreMovie(
    val genres: List<Genre>,
)

data class Genre (
    val id: Int,
    val name: String
)