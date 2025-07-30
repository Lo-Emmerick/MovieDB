package com.example.moviedb.details.navigation.listDetails

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.moviedb.navigation.listDetails.ListDetailsNavigation
import com.example.moviedb.navigation.listDetails.ListDetailsNavigationImpl
import com.example.moviedb.ui.listDetails.ListDetailsActivity
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class ListDetailsNavigationImplTest {

    private val navigation: ListDetailsNavigation = ListDetailsNavigationImpl()

    @Test
    fun getListDetailsIntentDeveRetornarListDetailsActivity() = runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val movieId = 123
        val information = "photos"

        val intent = navigation.getListDetailsIntent(context, movieId, information)

        Assert.assertEquals(ListDetailsActivity::class.java.name, intent.component?.className)
        Assert.assertTrue(intent.hasExtra(ListDetailsNavigation.Companion.MOVIE_DETAILS_ID))
        Assert.assertEquals(
            movieId,
            intent.getIntExtra(ListDetailsNavigation.Companion.MOVIE_DETAILS_ID, -1)
        )
    }
}