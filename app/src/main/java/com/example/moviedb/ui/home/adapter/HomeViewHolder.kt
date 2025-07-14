package com.example.moviedb.ui.home.adapter

import android.util.Log
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.moviedb.const.ApiConst.POSTER_PATH
import com.example.moviedb.databinding.ItemHomeBinding
import com.example.moviedb.model.MovieScreen

class HomeViewHolder(
    private val binding: ItemHomeBinding,
    private val listener: HomeListener
) : ViewHolder(binding.root) {
    fun bind(item: MovieScreen) {
        bindView(item)
        bindClick(item)
    }

    private fun bindView(item: MovieScreen) {
        binding.title.text = item.title
        binding.genre.text = item.genre + " • " + item.release_date.substring(0,4) + " | " + item.vote_average.toString().substring(0,3)
        Glide.with(binding.backdrop)
            .load(POSTER_PATH +item.poster_path)
            .fitCenter()
            .into(binding.backdrop)
    }

    private fun bindClick(item: MovieScreen) {
        binding.root.setOnClickListener{
            listener.onClickItem(item)
        }
    }
}