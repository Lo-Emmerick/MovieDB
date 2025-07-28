package com.example.moviedb.repository.details

import com.example.moviedb.model.Cast
import com.example.moviedb.model.DetailsCredits
import com.example.moviedb.model.DetailsMovie
import com.example.moviedb.model.DetailsPhotos
import com.example.moviedb.model.GenreDetails
import com.example.moviedb.model.Photo
import com.example.moviedb.network.Endpoint
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DetailsRepositoryImplTest {
    private val api: Endpoint = mockk()
    private lateinit var repository: DetailsRepository

    @Before
    fun setup() {
        repository = DetailsRepositoryImpl(api)
    }

    @Test
    fun `getMovieDetails deve retornar uma DetailsMovie`() = runTest {
        coEvery { api.getMovieDetails(any()) } returns DetailsMovie(
            poster_path = "poster_path",
            title = "Coraline",
            backdrop_path = "backdrop_path",
            vote_average = 5.5,
            runtime = 125,
            genres = listOf(GenreDetails(id = 2, name = "Drama")),
            overview = "Test unit"
        )

        val result = repository.getMovieDetails(1)

        assertEquals("poster_path", result.poster_path)
        assertEquals("Coraline", result.title)
        assertEquals("backdrop_path", result.backdrop_path)
        assertEquals(125, result.runtime)
        assertEquals(listOf(GenreDetails(2, "Drama")), result.genres)
        assertEquals("Test unit", result.overview)
    }

    @Test
    fun `getMovieCrew deve retornar uma DetailsCredits`() = runTest {
        coEvery { api.getMovieCrew(any()) } returns DetailsCredits(
            cast = listOf(
                Cast(
                    profile_path = "profile_path",
                    name = "Emma Watson",
                    character = "Hermione Granger"
                )
            )
        )

        val result = repository.getMovieCrew(1)

        assertEquals("profile_path", result.cast.first().profile_path)
        assertEquals("Emma Watson", result.cast.first().name)
        assertEquals("Hermione Granger", result.cast.first().character)
    }

    @Test
    fun `getMoviePhotos deve retornar uma DetailsPhotos`() = runTest {
        coEvery { api.getMoviePhotos(any()) } returns DetailsPhotos(
            backdrops = listOf(
                Photo(
                    file_path = "file_path",
                )
            )
        )

        val result = repository.getMoviePhotos(1)

        assertEquals("file_path", result.backdrops.first().file_path)
    }
}