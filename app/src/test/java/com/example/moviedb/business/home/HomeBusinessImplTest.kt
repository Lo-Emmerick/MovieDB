package com.example.moviedb.business.home

import com.example.moviedb.model.*
import com.example.moviedb.repository.genres.GenresRepository
import com.example.moviedb.repository.home.HomeRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class HomeBusinessImplTest {
    private val homeRepository: HomeRepository = mockk()
    private val genresRepository: GenresRepository = mockk()
    private lateinit var business: HomeBusiness

    private val movie = MovieAPI(
        id = 10,
        title = "Filme Sem Gênero",
        genre_ids = listOf(2),
        release_date = "2024-06-01",
        vote_average = 5.5,
        poster_path = "poster_path"
    )

    @Before
    fun setup() {
        business = HomeBusinessImpl(homeRepository, genresRepository)
    }

    @Test
    fun `getNowPlaying deve retornar uma lista de MovieScreen`() = runTest {
        val genres = listOf(Genre(2, "Drama"))

        val movieList = MovieList(
            results = listOf(
                movie,
            )
        )

        coEvery { homeRepository.getNowPlaying() } returns movieList

        val result = business.getNowPlaying(genres)

        assertEquals(1, result.size)
    }

    @Test
    fun `getNowPlaying deve retornar uma lista de MovieScreen com release data xxxx`() = runTest {
        val genres = listOf(Genre(2, "Drama"))

        val movieList = MovieList(
            results = listOf(
                movie.copy(
                    release_date = "",
                ),
            )
        )

        coEvery { homeRepository.getNowPlaying() } returns movieList

        val result = business.getNowPlaying(genres)

        assertEquals("xxxx", result.first().release_date)
    }
}
