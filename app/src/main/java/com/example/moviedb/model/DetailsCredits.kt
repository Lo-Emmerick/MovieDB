package com.example.moviedb.model

data class DetailsCredits (
    val cast: List<Cast>
)
data class Cast (
    val profile_path: String,
    val name: String,
    val character: String
)