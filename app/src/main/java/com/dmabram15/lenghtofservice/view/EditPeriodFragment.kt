package com.dmabram15.lenghtofservice.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dmabram15.lenghtofservice.R
import com.dmabram15.lenghtofservice.databinding.EditPeriodFragmentBinding
import com.dmabram15.lenghtofservice.model.Period
import com.dmabram15.lenghtofservice.model.listeners.OnMatcherEventListener
import com.dmabram15.lenghtofservice.model.utils.converters.DateConverter
import com.dmabram15.lenghtofservice.model.utils.enums.Multiples.*
import com.dmabram15.lenghtofservice.model.utils.matchers.OnInputTextStateChangeListener
import com.dmabram15.lenghtofservice.model.utils.matchers.RegexMaskTextWatcher
import com.dmabram15.lenghtofservice.viewModel.EditPeriodViewModel
import com.dmabram15.lenghtofservice.viewModel.SharedViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar

class EditPeriodFragment : Fragment(), OnInputTextStateChangeListener {

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

    private fun uiInit() {
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
        binding.beginDateInputText.apply {
            addTextChangedListener(
                RegexMaskTextWatcher(
                    this@EditPeriodFragment
                )
            )
        }

        binding.endDateInputText.apply {
            addTextChangedListener(
                RegexMaskTextWatcher(
                    this@EditPeriodFragment
                )
            )
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
        binding.beginDateInputText.setText(DateConverter.convert(value))
    }

    private fun renderEndDate(value: Long) {
        binding.endDateInputText.setText(DateConverter.convert(value))
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

    private fun getMatherEventListener(): OnMatcherEventListener {

        return object : OnMatcherEventListener {
            override fun hasBeginCross(crossedPeriod: Period, date: Long) {
                binding.beginDateInputLayout.isErrorEnabled = true
                binding.beginDateInputLayout.error = getString(R.string.crossing_period_error)
            }

            override fun hasEndCross(crossedPeriod: Period, date: Long) {
                binding.beginDateInputLayout.isErrorEnabled = true
                binding.beginDateInputLayout.error = getString(R.string.crossing_period_error)
            }

            override fun hasIncludingCross(crossedPeriod: Period) {
                binding.beginDateInputLayout.isErrorEnabled = true
                binding.beginDateInputLayout.error = getString(R.string.included_period_error)
            }

            override fun success() {
                snackBarShow(getString(R.string.saved_succesful))
            }
        }
    }

    //TODO удалить все к чертям отсюда
    override fun onInputError(message: String) {
        TODO("Not yet implemented")
    }

    override fun onInputSuccess() {
        TODO("Not yet implemented")
    }

    override fun onInputBegin() {
        TODO("Not yet implemented")
    }
}
