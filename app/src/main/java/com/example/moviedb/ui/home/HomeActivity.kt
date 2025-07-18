package com.example.moviedb.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.core.view.isVisible
import com.example.moviedb.databinding.ActivityHomeBinding
import com.example.moviedb.model.MovieScreen
import com.example.moviedb.navigation.details.DetailsNavigation
import com.example.moviedb.navigation.details.DetailsNavigationImpl
import com.example.moviedb.ui.home.adapter.HomeAdapter
import com.example.moviedb.ui.home.adapter.HomeListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : ComponentActivity(), HomeListener {
    private val navigationDetails: DetailsNavigation = DetailsNavigationImpl()
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindObserver()
        bindListener()
        viewModel.getGenres()
    }

    @SuppressLint("ServiceCast")
    private fun bindListener() {
        binding.radioNowPlaying.setOnClickListener {
            viewModel.getNowPlaying()
        }

        binding.radioComingSoon.setOnClickListener {
            viewModel.getUpComing()
        }

        binding.icSearch.setOnClickListener {
            binding.searchInput.isVisible = !binding.searchInput.isVisible
        }

        binding.searchInput.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = textView.text.toString().trim()
                if (query.isNotEmpty()) {
                    viewModel.searchMovies(query)
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding.searchInput.windowToken, 0)
                    binding.searchInput.clearFocus()
                    binding.radioGroup.isVisible = false
                } else {
                    showErrorScreen()
                }
                true
            } else {
                false
            }
        }

        binding.appName.setOnClickListener {
            binding.radioGroup.isVisible = true
            binding.searchInput.isVisible = false
            viewModel.getNowPlaying()
            binding.searchInput.text?.clear()
        }
    }

    private fun bindObserver() {
        viewModel.state.observe(this) {
            setDefaultState()
            when (it) {
                HomeState.Error -> showErrorScreen()
                HomeState.Loading -> showLoadingScreen()
                HomeState.Empty -> showEmptyState()
                is HomeState.Success -> showSuccessScreen(it.result)
            }
        }

        viewModel.genres.observe(this) {
            viewModel.getNowPlaying()
        }
    }

    private fun showEmptyState() {
        binding.recyclerView.isVisible = false
        binding.stateEmpty.root.isVisible = true
    }

    private fun showErrorScreen() {
        binding.stateError.root.isVisible = true
        binding.stateError.comeBack.isVisible = false
    }

    private fun showLoadingScreen() {
        binding.stateLoading.root.isVisible = true
    }

    private fun showSuccessScreen(movieAPIList: List<MovieScreen>) {
        binding.recyclerView.isVisible = true
        binding.recyclerView.adapter = HomeAdapter(movieAPIList, this)
    }

    private fun setDefaultState() {
        binding.recyclerView.isVisible = false
        binding.stateError.root.isVisible = false
        binding.stateLoading.root.isVisible = false
        binding.stateEmpty.root.isVisible = false
    }

    override fun onClickItem(item: MovieScreen) {
        val intent = navigationDetails.getDetailsIntent(baseContext, item.id)
        startActivity(intent)
    }
}