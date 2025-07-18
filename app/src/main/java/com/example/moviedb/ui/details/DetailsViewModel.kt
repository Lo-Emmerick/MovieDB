package com.example.moviedb.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedb.business.details.DetailsBusiness
import com.example.moviedb.ui.details.state.DetailsCastState
import com.example.moviedb.ui.details.state.DetailsPhotoState
import com.example.moviedb.ui.details.state.DetailsState
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val business: DetailsBusiness,
) : ViewModel() {

    private val _state = MutableLiveData<DetailsState>()
    val state: LiveData<DetailsState> = _state

    private val _castState = MutableLiveData<DetailsCastState>()
    val castState: LiveData<DetailsCastState> = _castState

    private val _photoState = MutableLiveData<DetailsPhotoState>()
    val photoState: LiveData<DetailsPhotoState> = _photoState

    fun dataMovie(movieId: Int) {
        _state.value = DetailsState.Loading
        viewModelScope.launch {
            try {
                val response = business.getMovieDetails(movieId)
                _state.value = DetailsState.Success(response)
            } catch (e: Exception) {
                _state.value = DetailsState.Error
            }
        }
    }

    fun getCast(movieId: Int) {
        _castState.value = DetailsCastState.Loading
        viewModelScope.launch {
            try {
                val response = business.getMovieCrew(movieId)
                _castState.value = if (response.cast.isNullOrEmpty()) {
                    DetailsCastState.Empty
                } else {
                    DetailsCastState.Success(response.cast)
                }
            } catch (e: Exception) {
                _castState.value = DetailsCastState.Error
            }
        }
    }

    fun getPhotos(movieId: Int) {
        _photoState.value = DetailsPhotoState.Loading
        viewModelScope.launch {
            try {
                val response = business.getMoviePhotos(movieId)
                _photoState.value = if (response.backdrops.isNullOrEmpty()) {
                    DetailsPhotoState.Empty
                } else {
                    DetailsPhotoState.Success(response.backdrops)
                }
            } catch (e: Exception) {
                _photoState.value = DetailsPhotoState.Error
            }
        }
    }
}