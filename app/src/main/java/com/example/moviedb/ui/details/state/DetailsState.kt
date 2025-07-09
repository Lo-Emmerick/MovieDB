package com.example.moviedb.ui.details.state

import com.example.moviedb.model.DetailsMovie
import com.example.moviedb.model.Photo

sealed interface DetailsState {
    data class Success(val result: DetailsMovie) : DetailsState
    object Loading : DetailsState
    object Error : DetailsState
}