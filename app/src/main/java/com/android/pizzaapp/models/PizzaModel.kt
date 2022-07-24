package com.android.pizzaapp.models

import com.google.gson.annotations.SerializedName

data class PizzaModel(
    @SerializedName("name")
    val name: String,
    @SerializedName("isVeg")
    val isVeg: Boolean,
    @SerializedName("description")
    val description: String,
    @SerializedName("defaultCrust")
    var crustId: Int,
    @SerializedName("crusts")
    val crustList: ArrayList<CrustModel>
) {
    var quantity: Int = 1
    fun getPrice(): Double? {
        val selectedCrust = crustList.find { crust -> crust.id == this.crustId }
        return selectedCrust?.availableSizes?.find { size -> size.id == selectedCrust.sizeId }?.price
    }

    fun getCrust(): CrustModel? {
        return crustList.find { it.id == crustId }
    }
}