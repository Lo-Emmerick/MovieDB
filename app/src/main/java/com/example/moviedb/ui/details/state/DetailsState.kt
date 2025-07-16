package com.example.moviedb.ui.details.state

import com.example.moviedb.model.DetailsMovieScreen

sealed interface DetailsState {
    data class Success(val result: DetailsMovieScreen) : DetailsState
    object Loading : DetailsState
    object Error : DetailsState
}