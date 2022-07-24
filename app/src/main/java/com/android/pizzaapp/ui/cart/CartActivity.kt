package com.android.pizzaapp.ui.cart

import android.content.Context
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import com.android.pizzaapp.databinding.ActivityCartBinding
import com.android.pizzaapp.databinding.ActivityPizzaListBinding

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this

        CartUI(binding).showList(supportFragmentManager, context)
    }
}