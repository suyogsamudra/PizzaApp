package com.android.pizzaapp.business

import android.content.Context
import com.android.pizzaapp.models.PizzaModel

object CartContainer {
    fun getCartContent(context: Context): ArrayList<PizzaModel> {
        return SharedPreferencesManager.getCartItems(context)
    }

    fun addToCart(context: Context, pizza: PizzaModel): ArrayList<PizzaModel> {
        return getCartContent(context).let { pizzaList ->
            alreadyPresent(pizzaList, pizza)?.let { it.quantity++ } ?: kotlin.run { pizzaList.add(pizza) }
            SharedPreferencesManager.setCartItems(context, pizzaList)
            pizzaList
        }
    }

    fun removeFromCart(context: Context, pizza: PizzaModel): ArrayList<PizzaModel> {
        return getCartContent(context).let { pizzaList ->
            alreadyPresent(pizzaList, pizza)?.let { if (it.quantity < 2) pizzaList.remove(pizza) else it.quantity-- }
            SharedPreferencesManager.setCartItems(context, pizzaList)
            pizzaList
        }
    }

    private fun alreadyPresent(pizzaList: ArrayList<PizzaModel>, pizza: PizzaModel): PizzaModel? {
        var pizzaModel: PizzaModel? = null
        pizzaList.filter { it.crustId == pizza.crustId }.forEach { p2 ->
            if (p2.getCrust()?.sizeId == pizza.getCrust()?.sizeId) {
                pizzaModel = p2
                return@forEach
            }
        }
        return pizzaModel
    }
}