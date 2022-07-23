package com.android.pizzaapp.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.android.pizzaapp.R
import com.android.pizzaapp.databinding.DialogInfoBsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class InfoBSDialog(
    private val title: String, private val description: String,
    private val finishActivity: Boolean, private val isDialogCancelable: Boolean = true,
    private val isConfirmation: Boolean = false, private val btnPositiveText: String = "", private val btnNegativeText: String = "",
    val btnPositiveClick: () -> Unit = {}, val btnNegativeClick: () -> Unit = {}, val btnOKClick: () -> Unit = {}
) : BottomSheetDialogFragment() {
    private lateinit var binding: DialogInfoBsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DialogInfoBsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setCanceledOnTouchOutside(isDialogCancelable)
        isCancelable = isDialogCancelable

        binding.txtErrorTitle.text = title
        binding.txtErrorDesc.text = description

        if (isConfirmation) {
            binding.lytConfirmButton.visibility = VISIBLE
            binding.btnOk.visibility = GONE

            binding.btnYes.text = btnPositiveText
            binding.btnNo.text = btnNegativeText
        } else {
            binding.lytConfirmButton.visibility = GONE
            binding.btnOk.visibility = VISIBLE
        }

        binding.btnOk.setOnClickListener {
            if (finishActivity) activity?.finish()
            else {
                btnOKClick()
                dismiss()
            }
        }

        binding.btnYes.setOnClickListener { btnPositiveClick() }
        binding.btnNo.setOnClickListener { btnNegativeClick() }
    }
}