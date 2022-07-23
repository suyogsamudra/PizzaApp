package com.android.pizzaapp.utils

fun Double.getChargesFormatted(prefix: String = "₹", suffix: String = "", decimalPlaces: Int = 2):
        String = prefix + String.format("%.${decimalPlaces}f", this) + suffix