package com.android.pizzaapp.ui.cart

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.pizzaapp.R
import com.android.pizzaapp.business.CartContainer
import com.android.pizzaapp.databinding.ActivityCartBinding
import com.android.pizzaapp.databinding.CartCellBinding
import com.android.pizzaapp.models.PizzaModel
import com.android.pizzaapp.utils.InfoBSDialog
import com.android.pizzaapp.utils.getChargesFormatted

interface CartUIInterface {
    fun showList(manager: FragmentManager, context: Context)
}

class CartUI(private val binding: ActivityCartBinding) : CartUIInterface {
    private var adapter: CartaListRecAdapter? = null

    @SuppressLint("NotifyDataSetChanged")
    override fun showList(manager: FragmentManager, context: Context) {
        with(binding) {
            recPizzaList.layoutManager = LinearLayoutManager(context)
            val list = CartContainer.getCartContent(context)
            updateOrderTotal(context, list)
            adapter = CartaListRecAdapter(list, { pos, pizza -> handleAdd(context, pos, pizza) },
                { pos, pizza -> handleRemove(context, manager, pos, pizza) })

            recPizzaList.adapter = adapter

            binding.btnConfirmOrder.setOnClickListener {
                Toast.makeText(context, context.getString(R.string.coming_soon), Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handleRemove(context: Context, manager: FragmentManager, pos: Int, pizza: PizzaModel) {
        if (pizza.quantity > 1) {
            CartContainer.removeFromCart(context, pizza).let {
                adapter?.updateList(it)
                updateOrderTotal(context, it)
            }
            adapter?.notifyItemChanged(pos)
        } else {
            InfoBSDialog(context.getString(R.string.remove_title), context.getString(R.string.remove_desc),
                finishActivity = false, isDialogCancelable = false, true,
                context.getString(R.string.confirmation_dialog_no),
                context.getString(R.string.confirm_remove), {
                }, {
                    CartContainer.removeFromCart(context, pizza).let {
                        updateOrderTotal(context, it)
                        if (it.isEmpty()) {
                            binding.recPizzaList.visibility = GONE
                            binding.btnConfirmOrder.visibility = GONE
                            binding.txtOrderTotal.visibility = GONE
                            binding.txtEmpty.visibility = VISIBLE
                            binding.txtEmpty.text = context.getString(R.string.cart_empty)
                        }
                        adapter?.updateList(it)
                    }
                    binding.recPizzaList.adapter?.notifyDataSetChanged()
                }).show(manager, "RemoveConfirmation")
        }
    }

    private fun handleAdd(context: Context, pos: Int, pizza: PizzaModel) {
        CartContainer.addToCart(context, pizza).let {
            adapter?.updateList(it)
            updateOrderTotal(context, it)
        }
        adapter?.notifyItemChanged(pos)
    }

    private fun updateOrderTotal(context: Context, list: ArrayList<PizzaModel>) {
        binding.txtOrderTotal.text = context.getString(
            R.string.order_total,
            list.sumOf { it.quantity * (it.getPrice() ?: 0.0) }.getChargesFormatted(suffix = "/-")
        )
    }
}

private class CartaListRecAdapter(
    private var pizzasList: ArrayList<PizzaModel>,
    private val addBtnCallback: (pos: Int, pizza: PizzaModel) -> Unit,
    private val removeBtnCallback: (pos: Int, pizza: PizzaModel) -> Unit
) : RecyclerView.Adapter<CartaListRecAdapter.ViewHolder>() {

    class ViewHolder(val binding: CartCellBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(CartCellBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            pizzasList[position].let { pizza ->
                txtName.text = pizza.name
                txtDescription.text = holder.itemView.context.getString(
                    R.string.size_desc, pizza.getCrust()?.name,
                    pizza.getCrust()?.getSelectedSize()?.name
                )
                txtPrice.text = ((pizza.getPrice() ?: 0.0) * pizza.quantity).getChargesFormatted()
                imgVeg.setImageResource(if (pizza.isVeg) R.drawable.ic_veg else R.drawable.non_veg)
                txtQty.text = pizza.quantity.toString()
                imgAdd.setOnClickListener { addBtnCallback(position, pizza) }
                imgRemove.setOnClickListener { removeBtnCallback(position, pizza) }
            }
        }
    }

    override fun getItemCount(): Int = pizzasList.size

    fun updateList(newList: ArrayList<PizzaModel>) {
        pizzasList = newList
    }
}