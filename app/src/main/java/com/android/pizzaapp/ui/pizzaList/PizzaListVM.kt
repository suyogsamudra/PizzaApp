package com.android.pizzaapp.ui.pizzaList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.pizzaapp.models.ApiResult
import com.android.pizzaapp.models.PizzaModel
import com.android.pizzaapp.ui.PizzaDetailsRepo
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import org.json.JSONObject

class PizzaListVM : ViewModel() {
    private val repository = PizzaDetailsRepo()
    val obsResponse = MutableLiveData<ApiResult<PizzaModel>>()

    fun getPizzaData() {
       /* obsResponse.postValue(ApiResult(data = Gson().fromJson("{\n" +
                "\"name\": \"Non-Veg Pizza\",\n" +
                "\"isVeg\": false,\n" +
                "\"description\": \"Lorem ipsum dolor sit amet, consectetur\n" +
                "adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore\n" +
                "magna aliqua.\",\n" +
                "\"defaultCrust\": 1,\n" +
                "\"crusts\": [{\n" +
                "\"id\": 1,\n" +
                "\"name\": \"Hand-tossed\",\n" +
                "\"defaultSize\": 2,\n" +
                "\"sizes\": [{\n" +
                "\"id\": 1,\n" +
                "\"name\": \"Regular\",\n" +
                "\"price\": 235.0\n" +
                "}, {\n" +
                "\"id\": 2,\n" +
                "\"name\": \"Medium\",\n" +
                "\"price\": 265.0\n" +
                "}, {\n" +
                "\"id\": 3,\n" +
                "\"name\": \"Large\",\n" +
                "\"price\": 295.0\n" +
                "}]\n" +
                "}, {\n" +
                "\"id\": 2,\n" +
                "\"name\": \"Cheese Burst\",\n" +
                "\"defaultSize\": 1,\n" +
                "\"sizes\": [{\n" +
                "\"id\": 1,\n" +
                "\"name\": \"Medium\",\n" +
                "\"price\": 295.0\n" +
                "}, {\n" +
                "\"id\": 2,\n" +
                "\"name\": \"Large\",\n" +
                "\"price\": 325.0\n" +
                "}]\n" +
                "}]\n" +
                "}", PizzaModel::class.java)))*/

        viewModelScope.launch {
            obsResponse.postValue(repository.getPizzaData())
        }
    }
}