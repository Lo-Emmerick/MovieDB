package com.example.moviedb.navigation.details

import android.content.Context
import android.content.Intent
import com.example.moviedb.navigation.details.DetailsNavigation.Companion.MOVIE_ID
import com.example.moviedb.ui.details.DetailsActivity

class DetailsNavigationImpl : DetailsNavigation {
    override fun getDetailsIntent(context: Context, movieId: Int): Intent {
        return Intent(context, DetailsActivity::class.java).apply {
            putExtra(MOVIE_ID, movieId)
        }
    }
}