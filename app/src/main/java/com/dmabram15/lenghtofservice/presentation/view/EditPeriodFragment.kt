package com.dmabram15.lenghtofservice.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dmabram15.lenghtofservice.R
import com.dmabram15.lenghtofservice.data.repository.PeriodsRepository
import com.dmabram15.lenghtofservice.databinding.EditPeriodFragmentBinding
import com.dmabram15.lenghtofservice.model.utils.enums.Multiples.*
import com.dmabram15.lenghtofservice.presentation.view.stringprovider.CalendarStringProviderImpl
import com.dmabram15.lenghtofservice.presentation.view.stringprovider.MessageStringProviderImpl
import com.dmabram15.lenghtofservice.presentation.view.viewmatcher.OnInputTextStateChangeListener
import com.dmabram15.lenghtofservice.presentation.view.viewmatcher.RegexMaskTextWatcher
import com.dmabram15.lenghtofservice.presentation.viewModel.converters.DateConverter
import com.dmabram15.lenghtofservice.presentation.viewModel.periodmatcher.MatcherResultMessage
import com.dmabram15.lenghtofservice.presentation.viewModel.viewmodel.EditPeriodViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar

class EditPeriodFragment : Fragment(), OnInputTextStateChangeListener {

    private val viewModel: EditPeriodViewModel
        by viewModels { EditPeriodViewModel.Factory(MessageStringProviderImpl(requireContext())) }

    private lateinit var binding: EditPeriodFragmentBinding

    private var datePicker = materialDatePickerInitialization()
    private var dateConverter : DateConverter? = null

    //dagger
    private val repository = PeriodsRepository.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EditPeriodFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        viewModel.setPeriodById(repository, periodId, dateConverter!!)
    }

    private fun setObservers() {
        viewModel.getBeginLD().observe(viewLifecycleOwner, { renderBeginDate(it) })
        viewModel.getEndLD().observe(viewLifecycleOwner, { renderEndDate(it) })
        viewModel.getMultiplyLD().observe(viewLifecycleOwner, { renderMultiply(it) })

        viewModel.getStartErrorMessage().observe(viewLifecycleOwner, { renderCrossBeginMessage(it) })
        viewModel.getEndErrorMessage().observe(viewLifecycleOwner, { renderCrossEndMessage(it) })
        viewModel.getSuccessMessage().observe(viewLifecycleOwner, { renderSuccessMessage(it) })

        viewModel.getBeginDateLong().observe(viewLifecycleOwner, {showBeginCalendar(it)})
        viewModel.getEngDateLong().observe(viewLifecycleOwner, {showEndCalendar(it)})
    }

    private fun showEndCalendar(it: Long?) {
        val title = getString(R.string.set_end_date)
        val onClickListener = { dateLong: Long ->
            viewModel.setEndDate(dateLong, dateConverter!!)
        }
        if (it == null || it == 0L) {
            pickerShowWithDate(System.currentTimeMillis(), title, onClickListener)
        }
        else {
            pickerShowWithDate(it, title, onClickListener)
        }
    }

    private fun showBeginCalendar(it: Long?) {
        val title = getString(R.string.set_begin_date)
        val onClickListener = { dateLong: Long ->
            viewModel.setStartDate(dateLong, dateConverter!!)
        }
        if (it == null || it == 0L) {
            pickerShowWithDate(System.currentTimeMillis(), title, onClickListener)
        }
        else {
            pickerShowWithDate(it, title, onClickListener)
        }
    }

    private fun renderSuccessMessage(it: MatcherResultMessage?) {
        it?.let {
            snackBarShow(it.message)
        }
    }

    private fun renderCrossEndMessage(it: MatcherResultMessage?) {
        it?.let {
            binding.endDateInputLayout.isErrorEnabled = true
            binding.endDateInputLayout.error = it.message
        }
    }

    private fun renderCrossBeginMessage(it: MatcherResultMessage?) {
        it?.let {
            binding.beginDateInputLayout.isErrorEnabled = true
            binding.beginDateInputLayout.error = it.message
        }
    }

    private fun setListeners() {

        binding.pickBeginDate.setOnClickListener {
            viewModel.fetchBeginDateLong()
        }

        binding.pickEndDate.setOnClickListener {
            viewModel.fetchEndDateLong()
        }

        binding.applyButton.setOnClickListener {
            viewModel.onSaveClicked(repository)
            activity?.onBackPressed()
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

    private fun renderBeginDate(value: String) {
        binding.beginDateInputText.setText(value)
    }

    private fun renderEndDate(value: String) {
        binding.endDateInputText.setText(value)
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

    }

    override fun onInputSuccess() {

    }

    override fun onInputBegin() {

    }

    private fun materialDatePickerInitialization() = MaterialDatePicker.Builder
        .datePicker()
        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
        .build()
}
