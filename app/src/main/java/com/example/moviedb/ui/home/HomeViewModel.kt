package com.example.moviedb.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.model.Genre
import com.example.moviedb.model.MovieList
import com.example.moviedb.model.MovieScreen
import com.example.moviedb.repository.genres.GenresRepository
import com.example.moviedb.repository.home.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: HomeRepository,
    private val repositoryGenres: GenresRepository
) : ViewModel() {
    private val _state = MutableLiveData<HomeState>()
    val state: LiveData<HomeState> = _state

    private val _genres = MutableLiveData<List<Genre>>()
    val genres: LiveData<List<Genre>> = _genres

    fun getGenres() {
        viewModelScope.launch {
            try {
                val response = repositoryGenres.getGenres()
                _genres.value = response.genres
            } catch (e: Exception) {
                //
            }
        }
    }

    private fun getMovieScreen(movieList: MovieList): List<MovieScreen> {
        val movieScreen = mutableListOf<MovieScreen>()

        movieList.results.forEach { item ->
            val genre = item.genre_ids.firstOrNull()?.let { id ->
                _genres.value?.find { it.id == id }?.name
            } ?: ""

            movieScreen.add(
                MovieScreen(
                    id = item.id,
                    title = item.title,
                    genre = genre.replace(" ", ""),
                    release_date = item.release_date,
                    vote_average = item.vote_average,
                    poster_path = item.poster_path ?: ""
                )
            )
        }

        return movieScreen
    }


    fun getNowPlaying() {
        _state.value = HomeState.Loading
        viewModelScope.launch {
            try {
                val response = repository.getNowPlaying()
                val movieScreen = getMovieScreen(response)
                _state.value = HomeState.Success(movieScreen)
            } catch (e: Exception) {
                _state.value = HomeState.Error
            }
        }
    }

    fun getUpComing() {
        _state.value = HomeState.Loading
        viewModelScope.launch {
            try {
                val response = repository.getUpcoming()
                val movieScreen = getMovieScreen(response)
                _state.value = HomeState.Success(movieScreen)
            } catch (e: Exception) {
                _state.value = HomeState.Error
            }
        }
    }

    fun searchMovies(movieName: String) {
        _state.value = HomeState.Loading
        viewModelScope.launch {
            try {
                val response = repository.searchMovies(movieName)
                val resultList = response.results
                _state.value = if (resultList.isNullOrEmpty()) {
                    HomeState.Empty
                } else {
                    val movieScreen = getMovieScreen(response)
                    HomeState.Success(movieScreen)
                }
            } catch (e: Exception) {
                _state.value = HomeState.Error
            }
        }
    }
}