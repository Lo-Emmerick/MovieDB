package com.example.moviedb.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.repository.home.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: HomeRepository
) : ViewModel() {
    private val _state = MutableLiveData<HomeState>()
    val state: LiveData<HomeState> = _state

    fun getNowPlaying() {
        _state.value = HomeState.Loading
        viewModelScope.launch {
            try {
                val response = repository.getNowPlaying()
                _state.value = HomeState.Success(response.results)
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
                _state.value = HomeState.Success(response.results)
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
                    HomeState.Success(resultList)
                }
            } catch (e: Exception) {
                _state.value = HomeState.Error
            }
        }
    }
}