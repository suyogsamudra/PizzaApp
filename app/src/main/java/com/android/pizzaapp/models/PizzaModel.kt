package com.android.pizzaapp.models

data class PizzaModel(
    val name: String,
    val isVeg: Boolean,
    val description: String,
    val selectedCrustIndex: Int,
    val crustList: ArrayList<CrustModel>,
    var selectedCrust: CrustModel?
) {
    init {
        selectedCrust = crustList.find { it.id == selectedCrustIndex }
    }
}