package com.example.moviedb.ui.listDetails

import com.example.moviedb.model.Cast
import com.example.moviedb.model.Photo

interface ListDetailsState {

    data class SuccessCast(val result: List<Cast>) : ListDetailsState
    data class SuccessPhoto(val resultPhoto: List<Photo>) : ListDetailsState
    object Loading : ListDetailsState
    object Empty : ListDetailsState
    object Error : ListDetailsState
}