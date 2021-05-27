package com.dmabram15.lenghtofservice.view

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.transition.Transition
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.FragmentTransitionImpl
import androidx.recyclerview.widget.LinearLayoutManager
import com.dmabram15.lenghtofservice.R
import com.dmabram15.lenghtofservice.databinding.PeriodsOfFragmentBinding
import com.dmabram15.lenghtofservice.model.PeriodOfService
import com.dmabram15.lenghtofservice.view.adapters.PeriodsOfServiceRVAdapter
import com.dmabram15.lenghtofservice.viewModel.EditPeriodViewModel
import com.dmabram15.lenghtofservice.viewModel.PeriodsOfViewModel
import com.dmabram15.lenghtofservice.viewModel.SharedViewModel

class PeriodsOfServiceFragment : Fragment() {

    private val periodsAdapter = PeriodsOfServiceRVAdapter()
    private lateinit var binding: PeriodsOfFragmentBinding

    companion object {
        fun newInstance() = PeriodsOfServiceFragment()
    }

    private lateinit var viewModel: PeriodsOfViewModel
    private lateinit var sharedViewModel : SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PeriodsOfFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setListeners()
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.getPeriods().value?.let {
            render(it)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelsInit()
    }

    private fun viewModelsInit() {
        activity?.let {
            sharedViewModel = ViewModelProvider(it).get(SharedViewModel::class.java)
        }
        sharedViewModel.getPeriods().observe(this, { render(it) })
        sharedViewModel.loadData()

        viewModel = ViewModelProvider(this).get(PeriodsOfViewModel::class.java)


    }

    private fun render(periods: ArrayList<PeriodOfService>) {
        periodsAdapter.setPeriods(periods)
        showAlertIsNull()
    }

    private fun showAlertIsNull() {
        if (periodsAdapter.itemCount == 0) {
            binding.nothingShowBanner.visibility = View.VISIBLE
        }
        else binding.nothingShowBanner.visibility = View.GONE
    }

    private fun setListeners() {
        binding.addFloatingButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, EditPeriodFragment.newInstance())
                ?.setCustomAnimations(
                    FragmentTransaction.TRANSIT_ENTER_MASK,
                    FragmentTransaction.TRANSIT_EXIT_MASK,
                    FragmentTransaction.TRANSIT_FRAGMENT_OPEN,
                    FragmentTransaction.TRANSIT_FRAGMENT_CLOSE
                )
                ?.addToBackStack(null)
                ?.commitAllowingStateLoss()
        }
    }

    private fun setRecyclerView() {
        binding.periodsRecyclerView.apply {
            this.adapter = periodsAdapter
            val lm = LinearLayoutManager(context)
            lm.orientation = LinearLayoutManager.VERTICAL
            layoutManager = lm
        }
    }
}