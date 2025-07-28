package com.example.moviedb.repository.home

import com.example.moviedb.model.MovieAPI
import com.example.moviedb.model.MovieList
import com.example.moviedb.network.Endpoint
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class HomeRepositoryImplTest {
    private val api: Endpoint = mockk()
    private lateinit var repository: HomeRepository

    @Before
    fun setup() {
        repository = HomeRepositoryImpl(api)
    }

    val movieList = MovieList(
        results = listOf(
            MovieAPI(
                id = 10,
                title = "Coraline",
                genre_ids = listOf(2),
                release_date = "2024-06-01",
                vote_average = 5.5,
                poster_path = "poster_path"
            )
        )
    )

    @Test
    fun `getNowPlaying deve retornar uma MovieList`() = runTest {
        coEvery { api.getNowPlaying() } returns movieList

        val result = repository.getNowPlaying()

        assertEquals(10, result.results.first().id)
        assertEquals("Coraline", result.results.first().title)
        assertEquals(listOf(2), result.results.first().genre_ids)
        assertEquals("2024-06-01", result.results.first().release_date)
        assertEquals("poster_path", result.results.first().poster_path)
    }

    @Test
    fun `getUpcoming deve retornar uma MovieList`() = runTest {
        coEvery { api.getUpcoming() } returns movieList

        val result = repository.getUpcoming()

        assertEquals(10, result.results.first().id)
        assertEquals("Coraline", result.results.first().title)
        assertEquals(listOf(2), result.results.first().genre_ids)
        assertEquals("2024-06-01", result.results.first().release_date)
        assertEquals("poster_path", result.results.first().poster_path)
    }

    @Test
    fun `searchMovies deve retornar uma MovieList`() = runTest {
        coEvery { api.searchMovies(any()) } returns movieList

        val result = repository.searchMovies("Coraline")

        assertEquals(10, result.results.first().id)
        assertEquals("Coraline", result.results.first().title)
        assertEquals(listOf(2), result.results.first().genre_ids)
        assertEquals("2024-06-01", result.results.first().release_date)
        assertEquals("poster_path", result.results.first().poster_path)
    }
}