package com.example.moviedb.navigation.details

import android.content.Context
import android.content.Intent

interface DetailsNavigation {
    fun getDetailsIntent(context: Context, movieId: Int) : Intent
    companion object {
        const val MOVIE_ID = "MOVIE_ID"
    }
}