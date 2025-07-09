package com.example.moviedb.ui.details.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.moviedb.const.ApiConst.POSTER_PATH
import com.example.moviedb.databinding.ItemPhotoDetailsBinding
import com.example.moviedb.model.Photo

class DetailsPhotoViewHolder(
    private val binding: ItemPhotoDetailsBinding,
) : ViewHolder(binding.root) {

    fun bind(item: Photo) {
        bindView(item)
    }

    private fun bindView(item: Photo) {
        Glide.with(binding.image)
            .load(POSTER_PATH +item.file_path)
            .fitCenter()
            .into(binding.image)

    }

}