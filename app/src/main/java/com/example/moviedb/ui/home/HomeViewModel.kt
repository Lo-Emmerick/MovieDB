package com.example.moviedb.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.business.home.HomeBusiness
import com.example.moviedb.model.Genre
import kotlinx.coroutines.launch

class HomeViewModel(
    private val business: HomeBusiness,
) : ViewModel() {
    private val _state = MutableLiveData<HomeState>()
    val state: LiveData<HomeState> = _state

    private val _genres = MutableLiveData<List<Genre>>()
    val genres: LiveData<List<Genre>> = _genres

    fun getGenres() {
        viewModelScope.launch {
            val response = business.getGenres()
            _genres.value = response.genres
        }
    }

    fun getNowPlaying() {
        _state.value = HomeState.Loading
        viewModelScope.launch {
            try {
                val response = business.getNowPlaying(_genres.value)
                _state.value = HomeState.Success(response)
            } catch (e: Exception) {
                _state.value = HomeState.Error
            }
        }
    }

    fun getUpComing() {
        _state.value = HomeState.Loading
        viewModelScope.launch {
            try {
                val response = business.getUpcoming(_genres.value)
                _state.value = HomeState.Success(response)
            } catch (e: Exception) {
                _state.value = HomeState.Error
            }
        }
    }

    fun searchMovies(movieName: String) {
        _state.value = HomeState.Loading
        viewModelScope.launch {
            try {
                val response = business.searchMovies(movieName, _genres.value)
                _state.value = if (response.isNullOrEmpty()) {
                    HomeState.Empty
                } else {
                    HomeState.Success(response)
                }
            } catch (e: Exception) {
                _state.value = HomeState.Error
            }
        }
    }
}