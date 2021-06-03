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
import com.dmabram15.lenghtofservice.model.utils.converters.LongToDateConverter
import com.dmabram15.lenghtofservice.model.Period
import com.dmabram15.lenghtofservice.model.listeners.OnMatcherEventListener
import com.dmabram15.lenghtofservice.model.utils.enums.Multiples.*
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
    private var openedId = 0

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditPeriodViewModel::class.java)
        activity?.let { sharedViewModel = ViewModelProvider(it).get(SharedViewModel::class.java) }

        uiInit()
        periodInit()
        setObservers()
        setListeners()
    }

    private fun uiInit(){
        binding.apply {
            zeroChip.text = "${USUAL.multiple}"
            firstChip.text = "${REGIONAL.multiple}"
            secondChip.text = "${FLIGHT.multiple}"
            thirdChip.text = "${COMBAT.multiple}"
        }
    }

    private fun periodInit() {
        var period: Period? = null
        arguments?.let {
            val args = EditPeriodFragmentArgs.fromBundle(it)
            period = args.period
        }
        openedId = when (period) {
            null -> 0
            else -> {
                viewModel.setPeriod(period!!)
                period!!.id
            }
        }
    }

    private fun setObservers() {
        viewModel.getBeginLD().observe(viewLifecycleOwner, { renderBeginDate(it) })
        viewModel.getEndLD().observe(viewLifecycleOwner, { renderEndDate(it) })
        viewModel.getMultiplyLD().observe(viewLifecycleOwner, { renderMultiply(it) })
    }

    private fun setListeners() {

        binding.pickStartDate.setOnClickListener {
            val title = getString(R.string.set_begin_date)
            val date = viewModel.getStartDate()
            val onClickListener = { dateLong: Long ->
                viewModel.setBeginDate(dateLong)
            }
            pickerShowWithDate(date, title, onClickListener)
        }

        binding.pickEndDate.setOnClickListener {
            val title = getString(R.string.set_end_date)
            val date = viewModel.getEndDate()
            val onClickListener = { dateLong: Long ->
                viewModel.setEndDate(dateLong)
            }
            pickerShowWithDate(date, title, onClickListener)
        }

        binding.applyButton.setOnClickListener {
            if (openedId == 0) openedId = sharedViewModel.getNextId()
            viewModel.createPeriodOfService(openedId)?.let {
                if (sharedViewModel
                        .checkPeriodsCollision(it, getMatherEventListener())
                ) {
                    sharedViewModel.setPeriod(it)
                    activity?.onBackPressed()
                } else {
                    snackBarShow(getString(R.string.has_collision_periods))
                }
            } ?: snackBarShow(getString(R.string.not_all_fields_was_filled))
        }

        binding.multiplySelectorChipsGroup.setOnCheckedChangeListener { _, i ->
            when (i) {
                binding.zeroChip.id -> {
                    viewModel.setMultiple(USUAL.multiple)
                }
                binding.firstChip.id -> {
                    viewModel.setMultiple(REGIONAL.multiple)
                }
                binding.secondChip.id -> {
                    viewModel.setMultiple(FLIGHT.multiple)
                }
                binding.thirdChip.id -> {
                    viewModel.setMultiple(COMBAT.multiple)
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
        binding.multiplySelectorChipsGroup.check(
            when (value) {
                1.0f -> R.id.zeroChip
                1.5f -> R.id.firstChip
                2.0f -> R.id.secondChip
                else -> R.id.thirdChip
            }
        )
    }

    private fun pickerShowWithDate(date: Long, title: String, onClickListener: (Long) -> Unit) {
        datePicker = MaterialDatePicker.Builder.datePicker()
            .setSelection(date)
            .setTitleText(title)
            .build()
        datePicker.addOnPositiveButtonClickListener(onClickListener)
        datePicker.show(childFragmentManager, "")
    }

    private fun getMatherEventListener() : OnMatcherEventListener {
        //TODO убрать хардкод
        return object : OnMatcherEventListener {
            override fun hasBeginCross(crossedPeriod: Period, date: Long) {
                val result = "${LongToDateConverter.convert(date)} лежит в пределах ${crossedPeriod.id} периода"
                binding.beginPeriodDateTextView.text = result
            }

            override fun hasEndCross(crossedPeriod: Period, date: Long) {
                val result = "${LongToDateConverter.convert(date)} лежит в пределах ${crossedPeriod.id} периода"
                binding.endPeriodDateTextView.text = result
            }

            override fun hasIncludingCross(crossedPeriod: Period) {
                val result = "${crossedPeriod.id} включен в выбранный период"
                binding.endPeriodDateTextView.text = result
            }

            override fun success() {
                snackBarShow("Период успешно сохранен!")
            }
        }
    }
}
