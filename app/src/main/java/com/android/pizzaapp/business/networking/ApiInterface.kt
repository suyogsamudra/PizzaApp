package com.android.pizzaapp.business.networking

import com.android.pizzaapp.models.PizzaModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("v1/pizza/1")
    suspend fun getPizzaData() : Response<PizzaModel>
}