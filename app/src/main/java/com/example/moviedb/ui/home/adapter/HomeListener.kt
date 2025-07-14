package com.example.moviedb.ui.home.adapter

import com.example.moviedb.model.MovieScreen

interface HomeListener {
    fun onClickItem(item: MovieScreen)
}