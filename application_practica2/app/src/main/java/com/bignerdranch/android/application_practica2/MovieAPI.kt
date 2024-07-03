package com.bignerdranch.android.application_practica2

import com.bignerdranch.android.application_practica2.ui.models.Movie
import retrofit2.Call
import retrofit2.http.GET

interface MovieAPI {

    @GET("movies")
    fun getData(): Call<List<Movie>>
}