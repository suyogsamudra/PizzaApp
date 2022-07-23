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
    val selectedCrustIndex: Int,
    @SerializedName("crusts")
    val crustList: ArrayList<CrustModel>,
) {
    fun getPrice(): Double? {
        val selectedCrust = crustList.find { crust -> crust.id == this.selectedCrustIndex }
        return selectedCrust?.availableSizes?.find { size -> size.id == selectedCrust.selectedSizeIndex }?.price
    }
}