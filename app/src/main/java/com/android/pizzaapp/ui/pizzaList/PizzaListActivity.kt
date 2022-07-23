package com.android.pizzaapp.ui.pizzaList

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.pizzaapp.databinding.ActivityPizzaListBinding
import com.android.pizzaapp.models.handlerError
import com.android.pizzaapp.ui.CustomiseBSDialog
import com.android.pizzaapp.utils.isOnline

class PizzaListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPizzaListBinding
    private lateinit var uiInterface: PizzaListUIInterface
    private lateinit var viewModel: PizzaListVM
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPizzaListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this

        viewModel = ViewModelProvider(this)[PizzaListVM::class.java]
        uiInterface = PizzaListUI(context, binding)

        viewModel.obsResponse.observe(this) { response ->
            response?.error?.handlerError(supportFragmentManager, context)
                ?: response.data?.let { uiInterface.setInfo(it) { pizza ->
                    CustomiseBSDialog(pizza).show(supportFragmentManager, "Add")
                } }
        }

        if (isOnline(context)) viewModel.getPizzaData() else uiInterface.showError(supportFragmentManager)
    }
}