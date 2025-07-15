package com.example.moviedb.business.home

import com.example.moviedb.model.Genre
import com.example.moviedb.model.GenreMovie
import com.example.moviedb.model.MovieAPI
import com.example.moviedb.model.MovieScreen
import com.example.moviedb.repository.genres.GenresRepository
import com.example.moviedb.repository.home.HomeRepository

class HomeBusinessImpl(
    private val repository: HomeRepository,
    private val repositoryGenres: GenresRepository
) : HomeBusiness {
    private fun getMovieScreen(movieList: List<MovieAPI>, genres: List<Genre>?): List<MovieScreen> {
        val movieScreen = mutableListOf<MovieScreen>()

        movieList.forEach { item ->
            val genre = item.genre_ids.firstOrNull()?.let { id ->
                genres?.find { it.id == id }?.name
            } ?: ""

            movieScreen.add(
                MovieScreen(
                    id = item.id,
                    title = item.title,
                    genre = genre.replace(" ", ""),
                    release_date = item.release_date,
                    vote_average = item.vote_average,
                    poster_path = item.poster_path ?: ""
                )
            )
        }
        return movieScreen
    }

    override suspend fun getNowPlaying(genreList: List<Genre>?): List<MovieScreen> {
        val response = repository.getNowPlaying()
        return getMovieScreen(response.results, genreList)
    }

    override suspend fun getUpcoming(genreList: List<Genre>?): List<MovieScreen> {
        val response = repository.getUpcoming()
        return getMovieScreen(response.results, genreList)
    }

    override suspend fun searchMovies(movieName: String, genreList: List<Genre>?): List<MovieScreen> {
        val response = repository.searchMovies(movieName)
        return getMovieScreen(response.results, genreList)
    }

    override suspend fun getGenres(): GenreMovie {
        val response = repositoryGenres.getGenres()
        return response
    }
}