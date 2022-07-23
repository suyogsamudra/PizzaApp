package com.android.pizzaapp.models

import com.google.gson.annotations.SerializedName

data class PizzaSizeModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price : Double
)
