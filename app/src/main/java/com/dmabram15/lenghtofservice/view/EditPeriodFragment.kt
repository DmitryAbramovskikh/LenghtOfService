package com.dmabram15.lenghtofservice.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dmabram15.lenghtofservice.R
import com.dmabram15.lenghtofservice.viewModel.EditPeriodViewModel
import com.dmabram15.lenghtofservice.databinding.EditPeriodFragmentBinding
import com.dmabram15.lenghtofservice.model.LongToDateConverter
import com.dmabram15.lenghtofservice.viewModel.SharedViewModel
import com.google.android.material.datepicker.MaterialDatePicker

class EditPeriodFragment : Fragment() {

    companion object {
        fun newInstance() = EditPeriodFragment()
    }

    private lateinit var viewModel: EditPeriodViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var binding: EditPeriodFragmentBinding
    private var isBeginDatePickClicked: Boolean? = null

    private val dataPicker = MaterialDatePicker.Builder.datePicker().build()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EditPeriodFragmentBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditPeriodViewModel::class.java)
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        setObservers()
        setListeners()
    }

    private fun setObservers() {
        viewModel.getBeginLD().observe(this, { renderBeginDate(it) })
        viewModel.getEndLD().observe(this, { renderEndDate(it) })
        viewModel.getMultiplyLD().observe(this, { renderMultiply(it) })
    }

    private fun setListeners() {
        dataPicker.addOnPositiveButtonClickListener { dateLong ->
            isBeginDatePickClicked?.let {
                if (it) {
                    viewModel.setBeginDate(dateLong)
                } else {
                    viewModel.setEndDate(dateLong)
                }
            }
        }
        binding.pickStartDate.setOnClickListener {
            isBeginDatePickClicked = true
            showPicker()
        }
        binding.pickEndDate.setOnClickListener {
            isBeginDatePickClicked = false
            showPicker()
        }
        binding.applyButton.setOnClickListener {
            viewModel.createPeriodOfService()?.let {
                sharedViewModel.setPeriod(it)
            }
        }
        binding.multiplySelectorRadioGroup.setOnCheckedChangeListener { _, i ->
            when (i) {
                binding.x10multiplierRB.id -> {
                    viewModel.setMultiple(1.0f)
                }
                binding.x15multiplierRB.id -> {
                    viewModel.setMultiple(1.5f)
                }
                binding.x20multiplierRB.id -> {
                    viewModel.setMultiple(2.0f)
                }
                binding.x30multiplierRB.id -> {
                    viewModel.setMultiple(3.0f)
                }
            }
        }
    }

    private fun showPicker() {
        activity?.supportFragmentManager?.let {
            dataPicker.show(it, null)
        }
    }

    private fun renderBeginDate(value: Long) {
        binding.beginPeriodDateTextView.text = LongToDateConverter
            .convert(value)
    }

    private fun renderEndDate(value: Long) {
        binding.endPeriodDateTextView.text = LongToDateConverter
            .convert(value)
    }

    private fun renderMultiply(value: Float) {
        binding.multiplySelectorRadioGroup.check(
            when (value) {
                1.0f -> R.id.x10multiplierRB
                1.5f -> R.id.x15multiplierRB
                2.0f -> R.id.x20multiplierRB
                else -> R.id.x30multiplierRB
            }
        )
    }


}