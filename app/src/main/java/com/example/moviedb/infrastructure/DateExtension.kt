package com.example.moviedb.infrastructure

fun Int.getMovieRunTime():String{
    val runtime = this
    val horas = runtime / 60
    val minutos = runtime % 60
    return "${horas}h${minutos}m"
}