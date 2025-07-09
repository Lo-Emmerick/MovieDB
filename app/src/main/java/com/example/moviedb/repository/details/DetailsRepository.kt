package com.example.moviedb.repository.details

import com.example.moviedb.model.DetailsCredits
import com.example.moviedb.model.DetailsMovie
import com.example.moviedb.model.DetailsPhotos

interface DetailsRepository {
    suspend fun getMovieDetails(movieId: Int): DetailsMovie
    suspend fun getMovieCrew(movieId: Int): DetailsCredits
    suspend fun getMoviePhotos(movieId: Int): DetailsPhotos
}