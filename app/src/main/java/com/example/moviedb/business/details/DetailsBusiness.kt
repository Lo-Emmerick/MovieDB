package com.example.moviedb.business.details

import com.example.moviedb.model.DetailsCredits
import com.example.moviedb.model.DetailsMovieScreen
import com.example.moviedb.model.DetailsPhotos

interface DetailsBusiness {
    suspend fun getMovieDetails(movieId: Int): DetailsMovieScreen
    suspend fun getMovieCrew(movieId: Int): DetailsCredits
    suspend fun getMoviePhotos(movieId: Int): DetailsPhotos
}