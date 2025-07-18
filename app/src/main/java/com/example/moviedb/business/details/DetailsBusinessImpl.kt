package com.example.moviedb.business.details

import com.example.moviedb.infrastructure.getMovieRunTime
import com.example.moviedb.infrastructure.toGenreString
import com.example.moviedb.model.DetailsCredits
import com.example.moviedb.model.DetailsMovie
import com.example.moviedb.model.DetailsMovieScreen
import com.example.moviedb.model.DetailsPhotos
import com.example.moviedb.repository.details.DetailsRepository

class DetailsBusinessImpl(
    private val repository: DetailsRepository
) : DetailsBusiness {
    private fun getMovieDetailsScreen(detailsMovie: DetailsMovie): DetailsMovieScreen {
        val backdropPath =
            if (detailsMovie.backdrop_path.isNullOrEmpty()) "" else detailsMovie.backdrop_path
        val overview = if (detailsMovie.overview.isNullOrEmpty()) "" else detailsMovie.overview
        val posterPath =
            if (detailsMovie.poster_path.isNullOrEmpty()) "" else detailsMovie.poster_path

        return DetailsMovieScreen(
            poster_path = posterPath,
            title = detailsMovie.title,
            backdrop_path = backdropPath,
            vote_average = detailsMovie.vote_average.toString().substring(0, 3),
            runtime = detailsMovie.runtime.getMovieRunTime(),
            genres = detailsMovie.genres.toGenreString(),
            overview = overview
        )
    }

    override suspend fun getMovieDetails(movieId: Int): DetailsMovieScreen {
        val respose = repository.getMovieDetails(movieId)
        return getMovieDetailsScreen(respose)
    }

    override suspend fun getMovieCrew(movieId: Int): DetailsCredits {
        return repository.getMovieCrew(movieId)
    }

    override suspend fun getMoviePhotos(movieId: Int): DetailsPhotos {
        return repository.getMoviePhotos(movieId)
    }
}