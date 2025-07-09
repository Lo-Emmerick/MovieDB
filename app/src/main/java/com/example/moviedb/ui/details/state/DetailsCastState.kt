package com.example.moviedb.ui.details.state

import com.example.moviedb.model.Cast

sealed interface DetailsCastState {
    data class Success(val result: List<Cast>) : DetailsCastState
    data object Loading : DetailsCastState
    data object Error : DetailsCastState
    data object Empty : DetailsCastState
}