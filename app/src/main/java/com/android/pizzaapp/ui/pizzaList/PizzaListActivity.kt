package com.android.pizzaapp.ui.pizzaList

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.pizzaapp.databinding.ActivityPizzaListBinding
import com.android.pizzaapp.models.CrustModel
import com.android.pizzaapp.models.PizzaModel
import com.android.pizzaapp.models.PizzaSizeModel

class PizzaListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPizzaListBinding
    private lateinit var uiInterface: PizzaListUIInterface
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPizzaListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this

        uiInterface = PizzaListUI(context, binding)
        uiInterface.setList(
            arrayListOf(
                PizzaModel(
                    "Thin Crust", true, "Lot of cheese !!!",
                    1, arrayListOf(
                        CrustModel(
                            1, "Hand Tossed", 1,
                            arrayListOf(PizzaSizeModel(1, "Large", 300.00)), null
                        )
                    ), null
                ),
                PizzaModel(
                    "Chicken Pizza", false, "All time favourite chicken pizza",
                    1, arrayListOf(
                        CrustModel(
                            1, "Hand Tossed", 1,
                            arrayListOf(PizzaSizeModel(1, "Large", 300.00)), null
                        )
                    ), null
                )
            )
        )
    }
}