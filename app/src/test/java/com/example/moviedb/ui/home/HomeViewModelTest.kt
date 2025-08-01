package com.example.moviedb.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.moviedb.business.home.HomeBusiness
import com.example.moviedb.model.Genre
import com.example.moviedb.model.GenreMovie
import com.example.moviedb.model.MovieScreen
import io.mockk.coEvery
import io.mockk.mockk
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

class HomeViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = StandardTestDispatcher()
    private lateinit var viewModel: HomeViewModel
    private val business: HomeBusiness = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = HomeViewModel(business)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getGenres deve armazenar uma lista de generos`() = runTest {
        val expectedGenres = listOf(Genre(1, "Acao"), Genre(2, "Drama"))
        coEvery { business.getGenres() } returns GenreMovie(expectedGenres)

        viewModel.getGenres()
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(expectedGenres, viewModel.genres.value)
        assertEquals(HomeState.Loading, viewModel.state.value)
    }

    @Test
    fun `getGenres deve definir o estado de erro`() = runTest {
        coEvery { business.getGenres() } throws Exception()

        viewModel.getGenres()
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(HomeState.Error, viewModel.state.value)
    }

    @Test
    fun `getNowPlaying deve armazenar uma lista de MovieScreen`() = runTest {
        val genres = listOf(Genre(1, "Action"))
        val movies = listOf(
            MovieScreen(
                id = 1,
                title = "Now Playing Movie",
                genre = "Drama",
                release_date = "2024-01-01",
                vote_average = 5.5,
                poster_path = "poster_path"
            )
        )

        viewModel = HomeViewModel(business).apply {
            (this.genres as MutableLiveData).value = genres
        }

        coEvery { business.getNowPlaying(genres) } returns movies

        viewModel.getNowPlaying()
        advanceUntilIdle()

        assertEquals(HomeState.Success(movies), viewModel.state.value)
    }

    @Test
    fun `getNowPlaying deve definir o estado de erro`() = runTest {
        coEvery { business.getNowPlaying(any()) } throws Exception()

        viewModel.getNowPlaying()
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(HomeState.Error, viewModel.state.value)
    }

    @Test
    fun `getUpComing deve retornar uma lista de MovieScreen`() = runTest {
        val genres = listOf(Genre(1, "Action"))
        val movies = listOf(
            MovieScreen(
                id = 1,
                title = "Now Playing Movie",
                genre = "Drama",
                release_date = "2024-01-01",
                vote_average = 5.5,
                poster_path = "poster_path"
            )
        )

        viewModel = HomeViewModel(business).apply {
            (this.genres as MutableLiveData).value = genres
        }

        coEvery { business.getUpcoming(genres) } returns movies

        viewModel.getUpComing()
        advanceUntilIdle()

        assertEquals(HomeState.Success(movies), viewModel.state.value)
    }

    @Test
    fun `getUpComing deve definir o estado de erro`() = runTest {
        coEvery { business.getUpcoming(any()) } throws Exception()

        viewModel.getUpComing()
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(HomeState.Error, viewModel.state.value)
    }

    @Test
    fun `searchMovies deve retornar uma lista de MovieScreen`() = runTest {
        val genres = listOf(Genre(1, "Action"))
        val query = "Coraline"
        val movies = listOf(
            MovieScreen(
                id = 1,
                title = "Now Playing Movie",
                genre = "Drama",
                release_date = "2024-01-01",
                vote_average = 5.5,
                poster_path = "poster_path"
            )
        )

        viewModel = HomeViewModel(business).apply {
            (this.genres as MutableLiveData).value = genres
        }

        coEvery { business.searchMovies(query, genres) } returns movies

        viewModel.searchMovies(query)
        advanceUntilIdle()

        assertEquals(HomeState.Success(movies), viewModel.state.value)
    }

    @Test
    fun `searchMovies deve retornar estado vazio`() = runTest {
        val genres = listOf(Genre(1, "Action"))
        val query = "Coraline"

        viewModel = HomeViewModel(business).apply {
            (this.genres as MutableLiveData).value = genres
        }

        coEvery { business.searchMovies(query, genres) } returns emptyList()

        viewModel.searchMovies(query)
        advanceUntilIdle()

        assertEquals(HomeState.Empty, viewModel.state.value)
    }

    @Test
    fun `searchMovies deve definir o estado de erro`() = runTest {
        val genres = listOf(Genre(1, "Terror"))
        val query = "Coraline"

        viewModel = HomeViewModel(business).apply {
            (this.genres as MutableLiveData).value = genres
        }

        coEvery { business.searchMovies(query, genres) } throws Exception()

        viewModel.searchMovies(query)
        advanceUntilIdle()

        assertEquals(HomeState.Error, viewModel.state.value)
    }
}