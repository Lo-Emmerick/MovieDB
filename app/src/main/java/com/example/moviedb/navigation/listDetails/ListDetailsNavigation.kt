package com.example.moviedb.navigation.listDetails

import android.content.Context
import android.content.Intent

interface ListDetailsNavigation {
    fun getListDetailsIntent(context: Context, movieId: Int, information: String) : Intent
    companion object {
        const val MOVIE_DETAILS_ID = "MOVIE_DETAILS_ID"
        const val INFORMATION = "INFORMATION"
    }
}