package com.example.moviedb.model

data class DetailsPhotos (
    val backdrops: List<Photo>
)
data class Photo (
    val file_path: String
)