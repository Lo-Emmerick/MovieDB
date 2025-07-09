package com.example.moviedb.infrastructure

import com.example.moviedb.model.GenreDetails

fun List<GenreDetails>.toGenreString(limit: Int = 3): String {
    var genreFilm = ""
    var count = 0

    for (genre in this) {
        if (count < limit) {
            genreFilm += genre.name + ", "
            count++
        }
    }

    if (genreFilm.endsWith(", ")) {
        genreFilm = genreFilm.dropLast(2)
    }

    return genreFilm
}
