package com.dmabram15.lenghtofservice.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dmabram15.lenghtofservice.R
import com.dmabram15.lenghtofservice.databinding.LenghtOfServiceFragmentBinding
import com.dmabram15.lenghtofservice.model.LongToDateConverter
import com.dmabram15.lenghtofservice.model.PeriodOfService
import com.dmabram15.lenghtofservice.viewModel.LengthOfServiceViewModel
import com.dmabram15.lenghtofservice.viewModel.SharedViewModel
import java.util.ArrayList

class LengthOfServiceFragment : Fragment() {

    companion object {
        fun newInstance() = LengthOfServiceFragment()
        const val CALC_WITH_MULTIPLIER = 0
        const val CALC_WITHOUT_MULTIPLIER = 1
    }

    private lateinit var viewModel: LengthOfServiceViewModel
    private lateinit var binding: LenghtOfServiceFragmentBinding

    private val sharedViewModel by lazy {
        activity?.let {
            ViewModelProvider(it).get(SharedViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LenghtOfServiceFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel?.getPeriods()?.observe(viewLifecycleOwner, { renderData(it) })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        viewModel = ViewModelProvider(this).get(LengthOfServiceViewModel::class.java)
    }

    private fun setListeners() {
        binding.editPeriodsButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.setCustomAnimations(
                    R.anim.appear_from_rignt,
                    R.anim.disappear_to_left,
                    R.anim.appear_from_left,
                    R.anim.disappear_to_rignt
                )
                ?.replace(R.id.container, PeriodsOfServiceFragment.newInstance())
                ?.addToBackStack(null)
                ?.commitAllowingStateLoss()
        }
    }

    private fun renderData(periods: ArrayList<PeriodOfService>?) {
        periods?.let {
            binding.preferentialLengthOfServiceTextView.text = LongToDateConverter
                .convertDifferent(calculateAllPeriodsLength(it, CALC_WITH_MULTIPLIER))

            binding.calendarLengthOfServiceTextView.text = LongToDateConverter
                .convertDifferent(calculateAllPeriodsLength(it, CALC_WITHOUT_MULTIPLIER))
        }
    }

    private fun calculateAllPeriodsLength(
        periods: ArrayList<PeriodOfService>,
        calculateMethod: Int
    ): Long {
        var result: Long = 0
        when (calculateMethod) {
            CALC_WITH_MULTIPLIER -> {
                for (period in periods) {
                    result += ((period.endPeriod - period.beginPeriod) * period.multiple).toLong()
                }
            }
            CALC_WITHOUT_MULTIPLIER -> {
                for (period in periods) {
                    result += (period.endPeriod - period.beginPeriod)
                }
            }
        }
        return result
    }
}