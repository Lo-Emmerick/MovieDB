package com.example.moviedb.navigation.listDetails

import android.content.Context
import android.content.Intent
import com.example.moviedb.navigation.listDetails.ListDetailsNavigation.Companion.INFORMATION
import com.example.moviedb.navigation.listDetails.ListDetailsNavigation.Companion.MOVIE_DETAILS_ID
import com.example.moviedb.ui.listDetails.ListDetailsActivity

class ListDetailsNavigationImpl : ListDetailsNavigation {
    override fun getListDetailsIntent(context: Context, movieId: Int, information: String): Intent {
        return Intent(context, ListDetailsActivity::class.java).apply {
            putExtra(MOVIE_DETAILS_ID, movieId)
            putExtra(INFORMATION, information)
        }
    }
}