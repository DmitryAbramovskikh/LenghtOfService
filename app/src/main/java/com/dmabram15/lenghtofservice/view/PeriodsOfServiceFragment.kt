package com.dmabram15.lenghtofservice.view

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dmabram15.lenghtofservice.R
import com.dmabram15.lenghtofservice.databinding.PeriodsOfFragmentBinding
import com.dmabram15.lenghtofservice.model.PeriodOfService
import com.dmabram15.lenghtofservice.view.adapters.PeriodsOfServiceRVAdapter
import com.dmabram15.lenghtofservice.viewModel.PeriodsOfViewModel

class PeriodsOfServiceFragment : Fragment() {

    private val periodsAdapter = PeriodsOfServiceRVAdapter()
    private lateinit var binding: PeriodsOfFragmentBinding

    companion object {
        fun newInstance() = PeriodsOfServiceFragment()
    }

    private lateinit var viewModel: PeriodsOfViewModel

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PeriodsOfViewModel::class.java)
        viewModel.getPeriods().observe(this, { render(it) })
        viewModel.loadData()
    }

    private fun render(periods: ArrayList<PeriodOfService>) {
        periodsAdapter.setPeriods(periods)
        showAlertIsNull()
    }

    private fun showAlertIsNull() {
        if (periodsAdapter.itemCount == 0) {
            AlertDialog.Builder(activity)
                    .setTitle(getString(R.string.attention))
                    .setMessage(getString(R.string.list_of_periods_is_null_message))
                    .setPositiveButton(getString(R.string.allow)) { dialog, _ ->
                        dialog.cancel()
                    }
                    .create()
                    .show()
        }
    }

    private fun setListeners() {
        binding.addFloatingButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, EditPeriodFragment.newInstance())
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