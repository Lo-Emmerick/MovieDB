package com.example.moviedb.ui.listDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.repository.details.DetailsRepository
import kotlinx.coroutines.launch

class ListDetailsViewModel(
    private val repository: DetailsRepository
) : ViewModel() {

    private val _state = MutableLiveData<ListDetailsState>()
    val castState: LiveData<ListDetailsState> = _state

    private fun getCast(movieId: Int) {
        _state.value = ListDetailsState.Loading
        viewModelScope.launch {
            try {
                val response = repository.getMovieCrew(movieId)
                val castList = response.cast
                _state.value = if (castList.isNullOrEmpty()) {
                    ListDetailsState.Empty
                } else {
                    ListDetailsState.SuccessCast(castList)
                }
            } catch (e: Exception) {
                _state.value = ListDetailsState.Error
            }
        }
    }

    private fun getPhoto(movieId: Int) {
        _state.value = ListDetailsState.Loading
        viewModelScope.launch {
            try {
                val response = repository.getMoviePhotos(movieId)
                val photoList = response.backdrops
                _state.value = if (photoList.isNullOrEmpty()) {
                    ListDetailsState.Empty
                } else {
                    ListDetailsState.SuccessPhoto(photoList)
                }
            } catch (e: Exception) {
                _state.value = ListDetailsState.Error
            }
        }
    }

    fun setList(type: String, movieId: Int) {
        if (type == "cast") {
            getCast(movieId)
        } else {
            getPhoto(movieId)
        }
    }
}