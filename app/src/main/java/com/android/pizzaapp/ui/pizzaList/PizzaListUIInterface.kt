package com.android.pizzaapp.ui.pizzaList

import android.content.Context
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.pizzaapp.R
import com.android.pizzaapp.databinding.ActivityPizzaListBinding
import com.android.pizzaapp.databinding.PizzaCellBinding
import com.android.pizzaapp.models.PizzaModel
import com.android.pizzaapp.utils.InfoBSDialog
import com.android.pizzaapp.utils.getChargesFormatted

interface PizzaListUIInterface {
    fun setInfo(pizzasDetails: PizzaModel)
    fun showError(manager: FragmentManager)
}

internal class PizzaListUI(private val context: Context, private val binding: ActivityPizzaListBinding) : PizzaListUIInterface {

    init {
        binding.recPizzaList.layoutManager = LinearLayoutManager(context)
    }

    override fun setInfo(pizzasDetails: PizzaModel) {
        binding.progressBar.visibility = GONE
        binding.mainLayout.visibility = VISIBLE
        binding.recPizzaList.adapter = PizzaListRecAdapter(arrayListOf(pizzasDetails))
    }

    override fun showError(manager: FragmentManager) {
        binding.progressBar.visibility = GONE
        InfoBSDialog(
            context.getString(R.string.no_internet_error_title),
            context.getString(R.string.no_internet_error_desc), true
        ).show(manager, "Error")
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
                txtPrice.text = it.getPrice()?.getChargesFormatted()
                imgVeg.setImageResource(if (it.isVeg) R.drawable.ic_veg else R.drawable.non_veg)
            }
        }
    }

    override fun getItemCount(): Int = pizzasList.size
}