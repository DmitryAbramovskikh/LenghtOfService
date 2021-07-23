package com.dmabram15.lenghtofservice.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dmabram15.lenghtofservice.R
import com.dmabram15.lenghtofservice.data.repository.RoomRepository
import com.dmabram15.lenghtofservice.databinding.EditPeriodFragmentBinding
import com.dmabram15.lenghtofservice.viewModel.converters.DateConverter
import com.dmabram15.lenghtofservice.model.utils.enums.Multiples.*
import com.dmabram15.lenghtofservice.view.stringprovider.CalendarStringProviderImpl
import com.dmabram15.lenghtofservice.view.viewmatcher.OnInputTextStateChangeListener
import com.dmabram15.lenghtofservice.view.viewmatcher.RegexMaskTextWatcher
import com.dmabram15.lenghtofservice.viewModel.periodmatcher.MatcherResultMessage
import com.dmabram15.lenghtofservice.viewModel.viewmodel.EditPeriodViewModel
import com.dmabram15.lenghtofservice.viewModel.viewmodel.SharedViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar

class EditPeriodFragment : Fragment(), OnInputTextStateChangeListener {

    private lateinit var viewModel: EditPeriodViewModel
    private lateinit var binding: EditPeriodFragmentBinding

    private var datePicker = materialDatePickerInitialization()
    private var dateConverter : DateConverter? = null

    //dagger
    private val repository = RoomRepository.getInstance()

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

        context?.let {
            dateConverter = DateConverter(CalendarStringProviderImpl(it))
        }

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
        var periodId = 0
        arguments?.let {
            val args = EditPeriodFragmentArgs.fromBundle(it)
            periodId = args.periodId
        }
        viewModel.setPeriodById(repository, periodId)
    }

    private fun setObservers() {
        viewModel.getBeginLD().observe(viewLifecycleOwner, { renderBeginDate(it) })
        viewModel.getEndLD().observe(viewLifecycleOwner, { renderEndDate(it) })
        viewModel.getMultiplyLD().observe(viewLifecycleOwner, { renderMultiply(it) })

        viewModel.getStartErrorMessage().observe(viewLifecycleOwner, { renderStartMessage(it) })
        viewModel.getEndErrorMessage().observe(viewLifecycleOwner, { renderEndMessage(it) })
        viewModel.getSuccessMessage().observe(viewLifecycleOwner, { renderSuccessMessage(it) })
    }

    private fun renderSuccessMessage(it: MatcherResultMessage?) {
        it?.let {
            snackBarShow(it.message)
        }
    }

    private fun renderEndMessage(it: MatcherResultMessage?) {
        it?.let {
            binding.endDateInputLayout.isErrorEnabled = true
            binding.endDateInputLayout.error = it.message
        }
    }

    private fun renderStartMessage(it: MatcherResultMessage?) {
        it?.let {
            binding.beginDateInputLayout.isErrorEnabled = true
            binding.beginDateInputLayout.error = it.message
        }
    }

    private fun setListeners() {

        binding.pickStartDate.setOnClickListener {
            val title = getString(R.string.set_begin_date)
            val onClickListener = { dateLong: Long ->
                viewModel.setStartDate(dateLong, dateConverter!!)
            }
            pickerShowWithDate(date, title, onClickListener)
        }

        binding.pickEndDate.setOnClickListener {
            val title = getString(R.string.set_end_date)
            val onClickListener = { dateLong: Long ->
                viewModel.setEndDate(dateLong, dateConverter!!)
            }
            pickerShowWithDate(date, title, onClickListener)
        }

        binding.applyButton.setOnClickListener {

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

    private fun materialDatePickerInitialization() = MaterialDatePicker.Builder
        .datePicker()
        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
        .build()
}
