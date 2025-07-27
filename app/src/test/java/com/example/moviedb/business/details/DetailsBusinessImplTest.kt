package com.example.moviedb.business.details

import com.example.moviedb.model.Cast
import com.example.moviedb.model.DetailsCredits
import com.example.moviedb.model.DetailsMovie
import com.example.moviedb.model.DetailsPhotos
import com.example.moviedb.model.GenreDetails
import com.example.moviedb.model.Photo
import com.example.moviedb.repository.details.DetailsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DetailsBusinessImplTest {
    private val detailsRepository: DetailsRepository = mockk()
    private lateinit var business: DetailsBusiness


    @Before
    fun setup() {
        business = DetailsBusinessImpl(detailsRepository)
    }

    @Test
    fun `getMovieDetails deve retornar uma DetailsMovieScreen`() = runTest {
        coEvery { detailsRepository.getMovieDetails(any()) } returns DetailsMovie(
            poster_path = "poster_path",
            title = "Coraline",
            backdrop_path = "backdrop_path",
            vote_average = 5.5,
            runtime = 132,
            genres = listOf(GenreDetails(id = 2, name = "Drama")),
            overview = "Test unit"
        )

        val result = business.getMovieDetails(1)

        assertEquals("Coraline", result.title)
        assertEquals("poster_path", result.poster_path)
        assertEquals("backdrop_path", result.backdrop_path)
        assertEquals("2h12m", result.runtime)
        assertEquals("Drama", result.genres)
        assertEquals("Test unit", result.overview)
    }

    @Test
    fun `getMovieDetails deve lidar com campos nulos e vazios`() = runTest {
        coEvery { detailsRepository.getMovieDetails(any()) } returns DetailsMovie(
            poster_path = "",
            title = "Coraline",
            backdrop_path = "",
            vote_average = 7.8,
            runtime = 95,
            genres = emptyList(),
            overview = ""
        )

        val result = business.getMovieDetails(2)

        assertEquals("Coraline", result.title)
        assertEquals("", result.poster_path)
        assertEquals("", result.backdrop_path)
        assertEquals("1h35m", result.runtime)
        assertEquals("", result.genres)
        assertEquals("", result.overview)
        assertEquals("7.8", result.vote_average)
    }

    @Test
    fun `getMovieCrew deve retornar uma DetailsCredits`() = runTest {
        coEvery { detailsRepository.getMovieCrew(any()) } returns DetailsCredits(
            cast = listOf(
                Cast(
                    profile_path = "/profile_path.jpg",
                    name = "Emma Watson",
                    character = "Hermione Granger"
                )
            )
        )

        val results = business.getMovieCrew(1)

        assertEquals("", results.cast[0].profile_path)
        assertEquals("/profile_path.jpg", results.cast[0].profile_path)
        assertEquals("", results.cast[0].name)
        assertEquals("Emma Watson", results.cast[0].name)
        assertEquals("", results.cast[0].character)
        assertEquals("Hermione Granger", results.cast[0].character)
    }
    @Test
    fun `getMoviePhotos deve retornar uma DetailsPhotos`() = runTest {
        coEvery { detailsRepository.getMoviePhotos(any()) } returns DetailsPhotos(
            backdrops = listOf(
                Photo(
                    file_path = "/file_path.jpg",
                )
            )
        )

        val result = business.getMoviePhotos(1)

        assertEquals("", result.backdrops[0].file_path)
        assertEquals("/file_path.jpg", result.backdrops[0].file_path)
    }

}