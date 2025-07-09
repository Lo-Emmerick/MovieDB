package com.example.moviedb.ui.home

import com.example.moviedb.model.DetailsMovie
import com.example.moviedb.model.Movie

sealed interface HomeState {
    data class Success(val result: List<Movie>) : HomeState
    object Loading : HomeState
    object Empty : HomeState
    object Error : HomeState
}

