package com.example.moviedb.ui.home.adapter

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.moviedb.R
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
        binding.imageEmpty.root.isVisible = false
        binding.title.text = item.title
        binding.genre.text = item.genre + " • " + item.release_date.substring(
            0,
            4
        ) + " | " + item.vote_average.toString().substring(0, 3)
        if (item.poster_path.isNullOrEmpty()) {
            binding.imageEmpty.root.isVisible = true
            Glide.with(binding.backdrop)
                .load(R.color.black)
                .fitCenter()
                .into(binding.backdrop)
        } else {
            Glide.with(binding.backdrop)
                .load(POSTER_PATH + item.poster_path)
                .fitCenter()
                .into(binding.backdrop)
        }
    }

    private fun bindClick(item: MovieScreen) {
        binding.root.setOnClickListener {
            listener.onClickItem(item)
        }
    }
}