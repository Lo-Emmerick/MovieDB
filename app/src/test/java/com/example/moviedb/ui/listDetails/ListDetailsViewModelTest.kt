package com.example.moviedb.ui.listDetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.moviedb.model.Cast
import com.example.moviedb.model.DetailsCredits
import com.example.moviedb.model.DetailsPhotos
import com.example.moviedb.model.Photo
import com.example.moviedb.repository.details.DetailsRepository
import io.mockk.coEvery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ListDetailsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = StandardTestDispatcher()
    private lateinit var viewModel: ListDetailsViewModel
    private val repository: DetailsRepository = io.mockk.mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = ListDetailsViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `setList deve chamar o getCast para retornar uma lista de atores`() = runTest {
        val movieId = 1
        val credits = DetailsCredits(
            cast = listOf(
                Cast(
                    profile_path = "profile_path",
                    name = "Evan Peters",
                    character = "Killy"
                )
            )
        )

        coEvery { repository.getMovieCrew(movieId) } returns credits

        viewModel.setList("cast", movieId)
        advanceUntilIdle()

        assertEquals(ListDetailsState.SuccessCast(credits.cast), viewModel.castState.value)
    }

    @Test
    fun `setList deve retornar um getCast com estado vazio`() = runTest {
        val movieId = 1
        val emptyCredits = DetailsCredits(cast = emptyList())

        coEvery { repository.getMovieCrew(movieId) } returns emptyCredits

        viewModel.setList("cast", movieId)
        advanceUntilIdle()

        assertEquals(ListDetailsState.Empty, viewModel.castState.value)
    }

    @Test
    fun `setList deve chamar o getCast para retornar erro`() = runTest {
        val movieId = 1
        coEvery { repository.getMovieCrew(movieId) } throws Exception()

        viewModel.setList("cast", movieId)
        advanceUntilIdle()

        assertEquals(ListDetailsState.Error, viewModel.castState.value)
    }

    @Test
    fun `setList deve chamar o getPhoto para retornar uma lista de fotos`() = runTest {
        val movieId = 1
        val photos = DetailsPhotos(
            backdrops = listOf(
                Photo(
                    file_path = "file_path"
                )
            )
        )

        coEvery { repository.getMoviePhotos(movieId) } returns photos

        viewModel.setList("photo", movieId)
        advanceUntilIdle()

        assertEquals(ListDetailsState.SuccessPhoto(photos.backdrops), viewModel.castState.value)
    }

    @Test
    fun `setList deve retornar getPhoto com estado vazio`() = runTest {
        val movieId = 1
        val emptyPhotos = DetailsPhotos(backdrops = emptyList())

        coEvery { repository.getMoviePhotos(movieId) } returns emptyPhotos

        viewModel.setList("photo", movieId)
        advanceUntilIdle()

        assertEquals(ListDetailsState.Empty, viewModel.castState.value)
    }

    @Test
    fun `setList deve chamar o getPhoto para retornar erro`() = runTest {
        val movieId = 1
        coEvery { repository.getMoviePhotos(movieId) } throws Exception()

        viewModel.setList("photo", movieId)
        advanceUntilIdle()

        assertEquals(ListDetailsState.Error, viewModel.castState.value)
    }
}
