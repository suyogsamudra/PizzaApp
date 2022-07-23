package com.android.pizzaapp.ui.pizzaList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.pizzaapp.R
import com.android.pizzaapp.databinding.ActivityPizzaListBinding
import com.android.pizzaapp.databinding.PizzaCellBinding
import com.android.pizzaapp.models.PizzaModel
import com.android.pizzaapp.utils.getChargesFormatted

interface PizzaListUIInterface {
    fun setList(pizzasList: ArrayList<PizzaModel>)
}

internal class PizzaListUI(val context: Context, private val binding: ActivityPizzaListBinding) : PizzaListUIInterface {

    init {
        binding.recPizzaList.layoutManager = LinearLayoutManager(context)
    }

    override fun setList(pizzasList: ArrayList<PizzaModel>) {
        binding.recPizzaList.adapter = PizzaListRecAdapter(pizzasList)
    }
}

class PizzaListRecAdapter(private val pizzasList: ArrayList<PizzaModel>) :
    RecyclerView.Adapter<PizzaListRecAdapter.ViewHolder>() {

    class ViewHolder(val binding: PizzaCellBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(PizzaCellBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            pizzasList[position].let {
                txtName.text = it.name
                txtDescription.text = it.description
                txtPrice.text = it.selectedCrust?.selectedSize?.price?.getChargesFormatted()
            }
        }
    }

    override fun getItemCount(): Int = pizzasList.size
}