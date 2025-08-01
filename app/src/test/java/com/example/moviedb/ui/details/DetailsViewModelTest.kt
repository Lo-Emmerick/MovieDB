package com.example.moviedb.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.moviedb.business.details.DetailsBusiness
import com.example.moviedb.model.Cast
import com.example.moviedb.model.DetailsCredits
import com.example.moviedb.model.DetailsMovieScreen
import com.example.moviedb.model.DetailsPhotos
import com.example.moviedb.model.Photo
import com.example.moviedb.ui.details.state.DetailsCastState
import com.example.moviedb.ui.details.state.DetailsPhotoState
import com.example.moviedb.ui.details.state.DetailsState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailsViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = StandardTestDispatcher()
    private lateinit var viewModel: DetailsViewModel
    private val business: DetailsBusiness = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = DetailsViewModel(business)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `dataMovie deve retornar sucesso com detalhes do filme`() = runTest {
        val movieId = 1
        val detailsMovieScreen = DetailsMovieScreen(
            poster_path = "poster_path",
            title = "Coraline",
            backdrop_path = "backdrop_path",
            vote_average = "5,5",
            runtime = "2h30m",
            genres = "Drama",
            overview = "Teste"
        )

        coEvery { business.getMovieDetails(movieId) } returns detailsMovieScreen

        viewModel.dataMovie(movieId)
        advanceUntilIdle()

        Assert.assertEquals(
            DetailsState.Success(detailsMovieScreen),
            viewModel.state.value
        )
    }

    @Test
    fun `dataMovie deve retornar erro`() = runTest {
        val movieId = 1
        coEvery { business.getMovieDetails(movieId) } throws Exception()

        viewModel.dataMovie(movieId)
        advanceUntilIdle()

        Assert.assertEquals(DetailsState.Error, viewModel.state.value)
    }

    @Test
    fun `getCast deve retornar sucesso com lista de atores`() = runTest {
        val movieId = 1

        val castList = DetailsCredits(
            cast = listOf(
                Cast(
                    profile_path = "profile_path",
                    name = "Evan Peters",
                    character = "Killy"
                )
            )
        )

        coEvery { business.getMovieCrew(movieId) } returns castList

        viewModel.getCast(movieId)
        advanceUntilIdle()

        Assert.assertEquals(DetailsCastState.Success(castList.cast), viewModel.castState.value)
    }

    @Test
    fun `getCast deve retornar estado vazio`() = runTest {
        val movieId = 1
        val emptyCastList = DetailsCredits(cast = emptyList())

        coEvery { business.getMovieCrew(movieId) } returns emptyCastList

        viewModel.getCast(movieId)
        advanceUntilIdle()

        Assert.assertEquals(DetailsCastState.Empty,viewModel.castState.value)
    }


    @Test
    fun `getCast deve retornar erro`() = runTest {
        val movieId = 1
        coEvery { business.getMovieCrew(movieId) } throws Exception()

        viewModel.getCast(movieId)
        advanceUntilIdle()

        Assert.assertEquals(DetailsCastState.Error, viewModel.castState.value)
    }

    @Test
    fun `getPhoto deve retornar sucesso com lista de fotos`() = runTest {
        val movieId = 1

        val photoList = DetailsPhotos(
            backdrops = listOf(
                Photo(
                    file_path = "file_path",
                )
            )
        )

        coEvery { business.getMoviePhotos(movieId) } returns photoList

        viewModel.getPhotos(movieId)
        advanceUntilIdle()

        Assert.assertEquals(
            DetailsPhotoState.Success(photoList.backdrops),
            viewModel.photoState.value
        )
    }

    @Test
    fun `getPhoto deve retornar estado vazio quando lista de fotos for vazia`() = runTest {
        val movieId = 1
        val emptyPhotoList = DetailsPhotos(backdrops = emptyList())

        coEvery { business.getMoviePhotos(movieId) } returns emptyPhotoList

        viewModel.getPhotos(movieId)
        advanceUntilIdle()

        Assert.assertEquals(DetailsPhotoState.Empty,viewModel.photoState.value)
    }

    @Test
    fun `getPhoto deve retornar erro`() = runTest {
        val movieId = 1
        coEvery { business.getMoviePhotos(movieId) } throws Exception()

        viewModel.getPhotos(movieId)
        advanceUntilIdle()

        Assert.assertEquals(DetailsPhotoState.Error, viewModel.photoState.value)
    }
}