package com.example.moviedb.ui.details.state

import com.example.moviedb.model.Photo

interface DetailsPhotoState {
    data class Success(val resultPhoto: List<Photo>) : DetailsPhotoState
    object Loading : DetailsPhotoState
    object Error : DetailsPhotoState
    object Empty : DetailsPhotoState
}

