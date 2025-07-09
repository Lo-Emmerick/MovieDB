package com.example.moviedb.ui.home.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.moviedb.const.ApiConst.POSTER_PATH
import com.example.moviedb.databinding.ItemHomeBinding
import com.example.moviedb.model.Movie

class HomeViewHolder(
    private val binding: ItemHomeBinding,
    private val listener: HomeListener
) : ViewHolder(binding.root) {
    fun bind(item: Movie) {
        bindView(item)
        bindClick(item)
    }

    private fun bindView(item: Movie) {
        binding.title.text = item.title
        binding.genre.text = item.genre_ids.toString()
        binding.description.text = " " +item.release_date +" | "+item.popularity
        Glide.with(binding.backdrop)
            .load(POSTER_PATH +item.poster_path)
            .fitCenter()
            .into(binding.backdrop)
    }

    private fun bindClick(item: Movie) {
        binding.root.setOnClickListener{
            listener.onClickItem(item)
        }
    }
}