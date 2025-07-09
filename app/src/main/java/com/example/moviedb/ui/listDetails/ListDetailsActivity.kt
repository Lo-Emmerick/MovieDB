package com.example.moviedb.ui.listDetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.moviedb.databinding.ActivityListDetailsBinding
import com.example.moviedb.model.Cast
import com.example.moviedb.model.Photo
import com.example.moviedb.navigation.listDetails.ListDetailsNavigation.Companion.INFORMATION
import com.example.moviedb.navigation.listDetails.ListDetailsNavigation.Companion.MOVIE_DETAILS_ID
import com.example.moviedb.ui.listDetails.adapter.ListDetailsCastAdapter
import com.example.moviedb.ui.listDetails.adapter.ListDetailsPhotoAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListDetailsActivity : AppCompatActivity() {
    private val movieId: Int by lazy { intent.getIntExtra(MOVIE_DETAILS_ID, 0) }
    private val information: String by lazy { intent.getStringExtra(INFORMATION) ?: "" }
    private lateinit var binding: ActivityListDetailsBinding
    private val listDetailsViewModel: ListDetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindObserver()
        bindListener()
        listDetailsViewModel.setList(information, movieId)
    }

    private fun bindObserver() {
        listDetailsViewModel.castState.observe(this) {
            when (it) {
                ListDetailsState.Error -> showErrorScreen()
                ListDetailsState.Loading -> showLoadingScreen()
                ListDetailsState.Empty -> showEmptyScreen()
                is ListDetailsState.SuccessCast -> showSuccessScreenCast(it.result)
                is ListDetailsState.SuccessPhoto -> showSuccessScreenPhoto(it.resultPhoto)
            }
        }
    }

    private fun bindListener() {
        binding.comeBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        if (information == "cast") {
            binding.title.text = "Cast"
        } else {
            binding.title.text = "Photos"
        }
    }

    private fun setDefaultState() {
        binding.recyclerViewCast.isVisible = false
        binding.recyclerViewPhotos.isVisible = false
        binding.stateError.root.isVisible = false
        binding.stateEmpty.root.isVisible = false
        binding.stateLoadingCast.root.isVisible = false
        binding.stateLoadingPhotos.root.isVisible = false
    }

    private fun showErrorScreen() {
        setDefaultState()
        binding.stateError.root.isVisible = true
        binding.stateError.comeBack.isVisible = false
    }

    private fun showEmptyScreen() {
        setDefaultState()
        binding.stateEmpty.root.isVisible = true
    }

    private fun showLoadingScreen() {
        setDefaultState()
        if (information == "cast") {
            binding.stateLoadingCast.root.isVisible = true
            binding.stateEmpty.information.text = "No cast available"
        } else {
            binding.stateLoadingPhotos.root.isVisible = true
            binding.stateEmpty.information.text = "No photos available"
        }
    }

    private fun showSuccessScreenCast(listDetailsCredits: List<Cast>) {
        setDefaultState()
        binding.recyclerViewCast.isVisible = true
        binding.recyclerViewCast.adapter = ListDetailsCastAdapter(listDetailsCredits)
    }

    private fun showSuccessScreenPhoto(listDetailsPhotos: List<Photo>) {
        setDefaultState()
        binding.recyclerViewPhotos.isVisible = true
        binding.recyclerViewPhotos.adapter = ListDetailsPhotoAdapter(listDetailsPhotos)
    }

}