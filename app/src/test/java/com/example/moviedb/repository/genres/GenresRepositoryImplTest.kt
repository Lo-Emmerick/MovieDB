package com.example.moviedb.repository.genres

import com.example.moviedb.model.Genre
import com.example.moviedb.model.GenreMovie
import com.example.moviedb.network.Endpoint
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GenresRepositoryImplTest {
    private val api: Endpoint = mockk()
    private lateinit var repository: GenresRepository

    @Before
    fun setup() {
        repository = GenresRepositoryImpl(api)
    }

    @Test
    fun `getGenres deve retornar uma GenreMovie`() = runTest {
        coEvery { api.getGenres() } returns GenreMovie(
            genres = listOf(
                Genre(
                    id = 2,
                    name = "Drama"
                )
            )
        )

        val result = repository.getGenres()

        assertEquals(2, result.genres.first().id)
        assertEquals("Drama", result.genres.first().name)
    }
}