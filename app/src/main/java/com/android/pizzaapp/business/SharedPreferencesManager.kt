package com.android.pizzaapp.business

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.android.pizzaapp.models.PizzaModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SharedPreferencesManager {

    private const val PREFS = "gpf"
    private const val CART_ITEMS = "crtItems"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS, Activity.MODE_PRIVATE)
    }

    fun setCartItems(context: Context, pizzas: List<PizzaModel>) {
        getSharedPreferences(context).edit()?.let {
            it.putString(CART_ITEMS, Gson().toJson(pizzas))
            it.apply()
        }
    }

    private fun getCartString(context: Context): String? {
        return getSharedPreferences(context).getString(CART_ITEMS, "")
    }

    fun getCartItems(context: Context): ArrayList<PizzaModel> =
        getCartString(context)?.let { Gson().fromJson(it, object : TypeToken<ArrayList<PizzaModel>>() {}.type) } ?: arrayListOf()
}