package com.android.pizzaapp.ui

import com.android.pizzaapp.business.networking.ApiInterface
import com.android.pizzaapp.business.networking.RetrofitObject
import com.android.pizzaapp.models.ApiError
import com.android.pizzaapp.models.ApiResult
import com.android.pizzaapp.models.PizzaModel

internal class PizzaDetailsRepo {
    suspend fun getPizzaData(): ApiResult<PizzaModel> {
        return try {
            with(RetrofitObject.retrofit.create(ApiInterface::class.java).getPizzaData()) {
                if (isSuccessful) ApiResult(data = body()) else ApiResult(error = ApiError(code(), message()))
            }
        } catch (ex: Exception) {
            ApiResult(error = ApiError(message = ex.message, exception = ex))
        }
    }
}