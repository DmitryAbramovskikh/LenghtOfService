package com.dmabram15.lenghtofservice.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dmabram15.lenghtofservice.R
import com.dmabram15.lenghtofservice.data.repository.PeriodsRepository
import com.dmabram15.lenghtofservice.databinding.LenghtOfServiceFragmentBinding
import com.dmabram15.lenghtofservice.model.repository.Repository
import com.dmabram15.lenghtofservice.presentation.view.stringprovider.CalendarStringProviderImpl
import com.dmabram15.lenghtofservice.presentation.viewModel.stringproviders.CalendarStringProvider
import com.dmabram15.lenghtofservice.presentation.viewModel.viewmodel.LengthOfServiceViewModel

class LengthOfServiceFragment : Fragment() {

    private lateinit var viewModel: LengthOfServiceViewModel
    private lateinit var binding: LenghtOfServiceFragmentBinding

    //Инжектить с dagger
    private val repository : Repository = PeriodsRepository.getInstance()
    private var stringProvider : CalendarStringProvider? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LenghtOfServiceFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        stringProvider = CalendarStringProviderImpl(requireContext())

        setViewModel()
    }

    private fun setListeners() {
        binding.editPeriodsButton.setOnClickListener {
            findNavController().navigate(R.id.action_lengthOfServiceFragment_to_periodsOfServiceFragment)
        }
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this).get(LengthOfServiceViewModel::class.java)

        viewModel.lengthWithMultiplier.observe(viewLifecycleOwner, { renderLengthMultiple(it) })
        viewModel.lengthInCalendar.observe(viewLifecycleOwner, { renderLengthCalendar(it) })

        viewModel.fetchLengths(repository, stringProvider!!)
    }

    private fun renderLengthCalendar(it: String?) {
        it?.let {
            binding.calendarLengthOfServiceTextView.text = it
        }
    }

    private fun renderLengthMultiple(it: String?) {
        it?.let {
            binding.preferentialLengthOfServiceTextView.text = it
        }
    }
}