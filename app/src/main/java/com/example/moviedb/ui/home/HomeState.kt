package com.example.moviedb.ui.home

import com.example.moviedb.model.MovieScreen

sealed interface HomeState {
    data class Success(val result: List<MovieScreen>) : HomeState
    object Loading : HomeState
    object Empty : HomeState
    object Error : HomeState
}

