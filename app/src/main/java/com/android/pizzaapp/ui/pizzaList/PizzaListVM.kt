package com.android.pizzaapp.ui.pizzaList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.pizzaapp.models.ApiResult
import com.android.pizzaapp.models.PizzaModel
import com.android.pizzaapp.ui.PizzaDetailsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PizzaListVM : ViewModel() {
    private val repository = PizzaDetailsRepo()
    val obsResponse = MutableLiveData<ApiResult<PizzaModel>>()

    fun getPizzaData() {
        viewModelScope.launch(Dispatchers.IO) { obsResponse.postValue(repository.getPizzaData()) }
    }
}