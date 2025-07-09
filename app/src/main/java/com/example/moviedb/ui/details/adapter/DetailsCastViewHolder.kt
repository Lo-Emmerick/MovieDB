package com.example.moviedb.ui.details.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.moviedb.const.ApiConst.POSTER_PATH
import com.example.moviedb.databinding.ItemCastDetailsBinding
import com.example.moviedb.model.Cast

class DetailsCastViewHolder(
    private val binding: ItemCastDetailsBinding,
) :ViewHolder(binding.root) {

    fun bind(item: Cast) {
        bindView(item)
    }

    private fun bindView(item: Cast) {
        Glide.with(binding.image)
            .load(POSTER_PATH +item.profile_path)
            .circleCrop()
            .into(binding.image)

        binding.name.text = item.name+" ... "+item.character
    }

}