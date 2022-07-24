package com.android.pizzaapp.ui.pizzaList

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.pizzaapp.R
import com.android.pizzaapp.databinding.ActivityPizzaListBinding
import com.android.pizzaapp.models.handlerError
import com.android.pizzaapp.ui.cart.CartActivity
import com.android.pizzaapp.ui.customisationDialog.CustomiseBSDialog
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

        supportActionBar?.title = getString(R.string.serving_now)

        viewModel.obsResponse.observe(this) { response ->
            response?.error?.handlerError(supportFragmentManager, context, true)
                ?: response.data?.let {
                    uiInterface.setInfo(it) { pizza ->
                        CustomiseBSDialog(pizza) { uiInterface.updateButtonStatus() }.show(supportFragmentManager, "Add")
                    }
                }
        }

        if (isOnline(context)) viewModel.getPizzaData() else uiInterface.showError(supportFragmentManager)

        binding.btnViewCart.setOnClickListener { startActivity(Intent(context, CartActivity::class.java)) }
    }

    override fun onResume() {
        super.onResume()
        uiInterface.updateButtonStatus()
    }
}