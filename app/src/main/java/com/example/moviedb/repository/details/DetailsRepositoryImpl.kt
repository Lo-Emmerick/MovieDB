package com.example.moviedb.repository.details

import com.example.moviedb.model.DetailsCredits
import com.example.moviedb.model.DetailsMovie
import com.example.moviedb.model.DetailsPhotos
import com.example.moviedb.network.Endpoint
import com.example.moviedb.network.RetrofitInstance

class DetailsRepositoryImpl(
    private val api : Endpoint
) : DetailsRepository {

    override suspend fun getMovieDetails(movieId: Int): DetailsMovie {
        return api.getMovieDetails(movieId)
    }

    override suspend fun getMovieCrew(movieId: Int): DetailsCredits {
        return api.getMovieCrew(movieId)
    }

    override suspend fun getMoviePhotos(movieId: Int): DetailsPhotos {
        return api.getMoviePhotos(movieId)
    }
}