package com.example.moviedb.ui.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.moviedb.const.ApiConst.POSTER_PATH
import com.example.moviedb.databinding.ActivityDetailsBinding
import com.example.moviedb.infrastructure.getMovieRunTime
import com.example.moviedb.infrastructure.toGenreString
import com.example.moviedb.model.Cast
import com.example.moviedb.model.DetailsMovie
import com.example.moviedb.model.DetailsMovieScreen
import com.example.moviedb.model.Photo
import com.example.moviedb.navigation.details.DetailsNavigation.Companion.MOVIE_ID
import com.example.moviedb.navigation.listDetails.ListDetailsNavigation
import com.example.moviedb.navigation.listDetails.ListDetailsNavigationImpl
import com.example.moviedb.ui.details.adapter.DetailsCastAdapter
import com.example.moviedb.ui.details.adapter.DetailsPhotoAdapter
import com.example.moviedb.ui.details.state.DetailsCastState
import com.example.moviedb.ui.details.state.DetailsPhotoState
import com.example.moviedb.ui.details.state.DetailsState
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailsActivity : AppCompatActivity() {
    private val navigationListDetails: ListDetailsNavigation = ListDetailsNavigationImpl()
    private val movieId: Int by lazy { intent.getIntExtra(MOVIE_ID, 0) }
    private lateinit var binding: ActivityDetailsBinding
    private val detailsViewModel: DetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        bindListener()
        bindObserver()
        detailsViewModel.dataMovie(movieId)
        detailsViewModel.getCast(movieId)
        detailsViewModel.getPhotos(movieId)
    }

    private fun bindListener() {
        binding.showMore.setOnClickListener {
            if (binding.synopsis.maxLines == 4) {
                binding.synopsis.maxLines = Int.MAX_VALUE
            } else {
                binding.synopsis.maxLines = 4
            }
        }
        binding.comeBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.viewAll.setOnClickListener {
            val intent = navigationListDetails.getListDetailsIntent(baseContext, movieId, "cast")
            startActivity(intent)
        }
        binding.viewAllPhotos.setOnClickListener {
            val intent = navigationListDetails.getListDetailsIntent(baseContext, movieId, "photos")
            startActivity(intent)
        }
    }

    private fun bindObserver() {
        detailsViewModel.state.observe(this) {
            setDefaultState()
            when (it) {
                is DetailsState.Error -> showErrorScreen()
                is DetailsState.Loading -> showLoadingScreen()
                is DetailsState.Success -> showSuccessState(it.result)
            }
        }
        detailsViewModel.castState.observe(this) {
            setDefaultCast()
            when (it) {
                is DetailsCastState.Error -> showCastError()
                is DetailsCastState.Loading -> showCastLoading()
                is DetailsCastState.Success -> showCastSuccess(it.result)
                is DetailsCastState.Empty -> showCastEmpty()
            }
        }
        detailsViewModel.photoState.observe(this) {
            setDefaultPhoto()
            when (it) {
                is DetailsPhotoState.Error -> showPhotoError()
                is DetailsPhotoState.Loading -> showPhotoLoading()
                is DetailsPhotoState.Success -> showSuccessScreenPhoto(it.resultPhoto)
                is DetailsPhotoState.Empty -> showPhotoEmpty()
            }
        }
    }

    private fun setDefaultState() {
        binding.stateError.root.isVisible = false
        binding.stateLoading.root.isVisible = false
        binding.stateSuccess.isVisible = false
    }

    private fun showErrorScreen() {
        binding.stateError.root.isVisible = true
        binding.stateError.comeBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun showLoadingScreen() {
        binding.stateLoading.root.isVisible = true
    }

    private fun showSuccessState(detailsMovie: DetailsMovieScreen) {
        setContentView(binding.root)
        binding.stateSuccess.isVisible = true

        if (detailsMovie.backdrop_path.isNullOrEmpty()){
            binding.imageEmpty.root.isVisible = true
        } else {
            Glide.with(binding.imageDetails)
                .load(POSTER_PATH + detailsMovie.backdrop_path)
                .into(binding.imageDetails)
        }
        binding.nameFilmDetails.text = detailsMovie.title
        binding.scoreFilm.text = detailsMovie.vote_average
        binding.timeMovie.text = detailsMovie.runtime
        binding.textView.text = detailsMovie.genres

        if (detailsMovie.overview.isNullOrBlank()) {
            binding.synopsis.text = "No synopsis available"
        } else {
            binding.synopsis.text = detailsMovie.overview
        }
    }

    private fun setDefaultCast(){
        binding.recyclerViewCast.isVisible = false
        binding.castMessage.isVisible = false
        binding.stateCastLoading.root.isVisible = false
        binding.castContainer.isVisible = true
    }

    private fun showCastError(){
        binding.castMessage.isVisible = true
        binding.castMessage.text = "Cast not found for this movie."
    }

    private fun showCastEmpty(){
        binding.castMessage.isVisible = true
        binding.castMessage.text = "No cast available"
    }

    private fun showCastLoading(){
        binding.stateCastLoading.root.isVisible = true
    }

    private fun showCastSuccess(castList: List<Cast>) {
        binding.recyclerViewCast.isVisible = true
        binding.recyclerViewCast.adapter = DetailsCastAdapter(castList)
    }

    private fun setDefaultPhoto(){
        binding.recyclerViewPhoto.isVisible = false
        binding.photoMessage.isVisible = false
        binding.statePhotoLoading.root.isVisible = false
    }

    private fun showPhotoError(){
        binding.photoMessage.isVisible = true
        binding.photoMessage.text = "Photo not found for this movie."
    }

    private fun showPhotoEmpty(){
        binding.photoMessage.isVisible = true
        binding.photoMessage.text = "No photo available"
    }

    private fun showPhotoLoading(){
        binding.statePhotoLoading.root.isVisible = true
    }

    private fun showSuccessScreenPhoto(photoList: List<Photo>) {
        binding.recyclerViewPhoto.isVisible = true
        binding.recyclerViewPhoto.adapter = DetailsPhotoAdapter(photoList)
    }
}
