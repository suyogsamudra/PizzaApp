package com.android.pizzaapp.models

import com.google.gson.annotations.SerializedName

data class CrustModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("defaultSize")
    var sizeId: Int,
    @SerializedName("sizes")
    val availableSizes: ArrayList<PizzaSizeModel>,
) {
    fun getSelectedSize(): PizzaSizeModel? {
        return availableSizes.find { it.id == sizeId }
    }
}