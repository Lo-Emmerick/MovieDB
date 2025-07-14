package com.example.moviedb.network

import com.example.moviedb.const.ApiConst.TOKEN
import com.example.moviedb.model.DetailsCredits
import com.example.moviedb.model.DetailsMovie
import com.example.moviedb.model.DetailsPhotos
import com.example.moviedb.model.GenreMovie
import com.example.moviedb.model.MovieList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Endpoint {
    @GET("movie/now_playing$TOKEN")
    suspend fun getNowPlaying(): MovieList

    @GET("movie/upcoming$TOKEN")
    suspend fun getUpcoming(): MovieList

    @GET("movie/{movie_id}$TOKEN")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
    ): DetailsMovie

    @GET("movie/{movie_id}/credits$TOKEN")
    suspend fun getMovieCrew(
        @Path("movie_id") movieId: Int,
    ): DetailsCredits

    @GET("movie/{movie_id}/images$TOKEN")
    suspend fun getMoviePhotos(
        @Path("movie_id") movieId: Int,
    ): DetailsPhotos

    @GET("search/movie$TOKEN")
    suspend fun searchMovies(
        @Query("query") movieName: String,
    ): MovieList

    @GET("genre/movie/list$TOKEN")
    suspend fun getGenres(): GenreMovie
}