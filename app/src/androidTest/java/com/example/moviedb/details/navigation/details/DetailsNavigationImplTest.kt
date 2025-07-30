package com.example.moviedb.details.navigation.details

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.moviedb.navigation.details.DetailsNavigation
import com.example.moviedb.navigation.details.DetailsNavigationImpl
import com.example.moviedb.ui.details.DetailsActivity
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class DetailsNavigationImplTest {

    private val navigation: DetailsNavigation = DetailsNavigationImpl()

    @Test
    fun getDetailsIntentDeveRetornarDetailsActivity() = runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val movieId = 123

        val intent = navigation.getDetailsIntent(context, movieId)

        Assert.assertEquals(DetailsActivity::class.java.name, intent.component?.className)
        Assert.assertTrue(intent.hasExtra(DetailsNavigation.Companion.MOVIE_ID))
        Assert.assertEquals(movieId, intent.getIntExtra(DetailsNavigation.Companion.MOVIE_ID, -1))
    }
}