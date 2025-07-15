package com.example.moviedb.di

import com.example.moviedb.business.home.HomeBusiness
import com.example.moviedb.business.home.HomeBusinessImpl
import com.example.moviedb.const.ApiConst.BASE_URL
import com.example.moviedb.network.Endpoint
import com.example.moviedb.repository.details.DetailsRepository
import com.example.moviedb.repository.details.DetailsRepositoryImpl
import com.example.moviedb.repository.genres.GenresRepository
import com.example.moviedb.repository.genres.GenresRepositoryImpl
import com.example.moviedb.repository.home.HomeRepository
import com.example.moviedb.repository.home.HomeRepositoryImpl
import com.example.moviedb.ui.details.DetailsViewModel
import com.example.moviedb.ui.home.HomeViewModel
import com.example.moviedb.ui.listDetails.ListDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val loadRepositories = module {
    single{HomeRepositoryImpl(api = get()) as HomeRepository}
    single{DetailsRepositoryImpl(api = get()) as DetailsRepository}
    single {GenresRepositoryImpl(api = get()) as GenresRepository}
}

val loadBusiness = module {
    single{HomeBusinessImpl( repository = get(), repositoryGenres = get()) as HomeBusiness}
}

val loadViewModels = module {
    viewModel{ HomeViewModel(business = get())}
    viewModel{ DetailsViewModel(repository = get())}
    viewModel{ ListDetailsViewModel(repository = get())}
}

val movieApi = module {
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(Endpoint::class.java)
    }
}
