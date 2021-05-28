package com.dmabram15.lenghtofservice.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import com.dmabram15.lenghtofservice.R
import com.dmabram15.lenghtofservice.viewModel.EditPeriodViewModel
import com.dmabram15.lenghtofservice.databinding.EditPeriodFragmentBinding
import com.dmabram15.lenghtofservice.model.LongToDateConverter
import com.dmabram15.lenghtofservice.viewModel.SharedViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar

class EditPeriodFragment : Fragment() {

    companion object {
        fun newInstance() = EditPeriodFragment()
    }

    private lateinit var viewModel: EditPeriodViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var binding: EditPeriodFragmentBinding
    private var isBeginDatePickClicked: Boolean? = null

    private var datePicker = materialDatePickerInitialization()

    private fun materialDatePickerInitialization() = MaterialDatePicker.Builder
        .datePicker()
        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
        .build()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EditPeriodFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(EditPeriodViewModel::class.java)
        activity?.let { sharedViewModel = ViewModelProvider(it).get(SharedViewModel::class.java) }

        setObservers()
        setListeners()
    }

    private fun setObservers() {
        viewModel.getBeginLD().observe(this, { renderBeginDate(it) })
        viewModel.getEndLD().observe(this, { renderEndDate(it) })
        viewModel.getMultiplyLD().observe(this, { renderMultiply(it) })
    }

    private fun setListeners() {

        binding.pickStartDate.setOnClickListener {
            val title = getString(R.string.set_begin_date)
            val date = viewModel.getStartDate()
            val onClickListener = { dateLong : Long ->
                viewModel.setBeginDate(dateLong)
            }
            pickerShowWithDate(date, title, onClickListener)
        }

        binding.pickEndDate.setOnClickListener {
            val title = getString(R.string.set_begin_date)
            val date = viewModel.getEndDate()
            val onClickListener = { dateLong : Long ->
                viewModel.setEndDate(dateLong)
            }
            pickerShowWithDate(date, title, onClickListener)
        }

        binding.applyButton.setOnClickListener {
            viewModel.createPeriodOfService()?.let {
                if (sharedViewModel
                        .checkPeriodsCollision(it)
                ) {
                    sharedViewModel.setPeriod(it)
                    sharedViewModel.savePeriod(it)
                    fragmentManager?.popBackStack()
                } else {
                    snackBarShow(getString(R.string.has_collision_periods))
                }
            } ?: snackBarShow(getString(R.string.not_all_fields_was_filled))
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

    private fun snackBarShow(text: String) {
        Snackbar.make(
            binding.root,
            text,
            Snackbar.LENGTH_LONG
        ).show()
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

    private fun pickerShowWithDate(date : Long, title : String, onClickListener : (Long) -> Unit) {
        datePicker = MaterialDatePicker.Builder.datePicker()
            .setSelection(date)
            .setTitleText(title)
            .build()
        datePicker.addOnPositiveButtonClickListener(onClickListener)
        datePicker.show(childFragmentManager, "")
    }
}