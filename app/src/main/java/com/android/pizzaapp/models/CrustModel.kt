package com.android.pizzaapp.models

data class CrustModel(
    val id : Int,
    val name : String,
    val selectedSizeIndex : Int,
    val availableSizes : ArrayList<PizzaSizeModel>,
    var selectedSize : PizzaSizeModel?
){
    init {
        selectedSize = availableSizes.find { it.id == selectedSizeIndex }
    }
}
