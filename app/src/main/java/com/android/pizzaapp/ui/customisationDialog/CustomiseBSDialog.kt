package com.android.pizzaapp.ui.customisationDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.android.pizzaapp.R
import com.android.pizzaapp.business.CartContainer
import com.android.pizzaapp.databinding.CustomizeBsBinding
import com.android.pizzaapp.databinding.NameCellBinding
import com.android.pizzaapp.models.PizzaModel
import com.android.pizzaapp.models.PizzaSizeModel
import com.android.pizzaapp.utils.getChargesFormatted
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CustomiseBSDialog(private val pizzaModel: PizzaModel) : BottomSheetDialogFragment() {
    private lateinit var binding: CustomizeBsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = CustomizeBsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtPizzaName.text = pizzaModel.name
        binding.txtCrust.text = pizzaModel.getCrust()?.name

        setup(binding.recCrust)
        setup(binding.recSize)

        binding.recCrust.adapter = RecyclerCrustAdapter(pizzaModel.crustList.map {
            PizzaSizeModel(it.id, it.name, -1.00)
        }) {
            pizzaModel.crustId = it.id
            binding.txtCrust.text = it.name
            resetViews()
            setSizeAdapter()
            updatePrice()
        }

        setSizeAdapter()
        updatePrice()

        binding.txtCrust.setOnClickListener { toggleSelectors(true) }
        binding.txtSize.setOnClickListener { toggleSelectors(false) }

        binding.btnAdd.setOnClickListener {
            CartContainer.addToCart(requireContext(), pizzaModel)
            dismiss()
            Toast.makeText(context, getString(R.string.added_to_cart), LENGTH_LONG).show()
        }
    }

    private fun setSizeAdapter() {
        binding.recSize.adapter = RecyclerCrustAdapter(pizzaModel.getCrust()?.availableSizes!!) {
            //Purposefully updating defaultSize here, to remember the last selected combination
            //To preserve the default selection, simply introduce new variable. (selectedSize)
            pizzaModel.getCrust()?.sizeId = it.id
            binding.txtSize.text = it.name
            updatePrice()
            resetViews()
        }
    }

    private fun updatePrice() {
        binding.txtSize.text = pizzaModel.getCrust()?.let { crust ->
            crust.availableSizes.find { it.id == crust.sizeId }?.let { size ->
                binding.txtPrice.text = context?.getString(
                    R.string.total_amt,
                    size.price.getChargesFormatted(suffix = "/-")
                )
                size.name
            }
        }
    }

    private fun setup(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(DividerItemDecoration(context, VERTICAL))
    }

    private fun toggleSelectors(isCrust: Boolean) {
        resetViews()
        if (isCrust) {
            binding.recCrust.visibility = VISIBLE
            binding.txtCrust.visibility = GONE
        } else {
            binding.recSize.visibility = VISIBLE
            binding.txtSize.visibility = GONE
        }
    }

    private fun resetViews() {
        binding.recSize.visibility = GONE
        binding.recCrust.visibility = GONE

        binding.txtCrust.visibility = VISIBLE
        binding.txtSize.visibility = VISIBLE
    }
}

class RecyclerCrustAdapter(
    private val list: List<PizzaSizeModel>,
    private val onSelected: (selected: PizzaSizeModel) -> Unit
) : RecyclerView.Adapter<RecyclerCrustAdapter.ViewHolder>() {

    class ViewHolder(val binding: NameCellBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(NameCellBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            list[position].let { item ->
                txtName.text = item.name
                txtPrice.text = if (item.price > 0.0) item.price.getChargesFormatted() else ""
                holder.itemView.setOnClickListener { onSelected(item) }
            }
        }
    }

    override fun getItemCount(): Int = list.size
}