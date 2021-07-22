package com.dmabram15.lenghtofservice.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dmabram15.lenghtofservice.databinding.PeriodsOfFragmentBinding
import com.dmabram15.lenghtofservice.model.Period
import com.dmabram15.lenghtofservice.view.adapters.PeriodsOfServiceRVAdapter
import com.dmabram15.lenghtofservice.viewModel.viewmodel.PeriodsOfViewModel
import com.dmabram15.lenghtofservice.viewModel.viewmodel.SharedViewModel
import java.util.*

class PeriodsOfServiceFragment : Fragment() {

    private lateinit var periodsAdapter: PeriodsOfServiceRVAdapter
    private lateinit var binding: PeriodsOfFragmentBinding

    private lateinit var viewModel: PeriodsOfViewModel
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PeriodsOfFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelsInit()
        setRecyclerView()
        setListeners()
    }

    private fun viewModelsInit() {
        activity?.let {
            sharedViewModel = ViewModelProvider(it).get(SharedViewModel::class.java)
        }
        sharedViewModel.loadData()
        viewModel = ViewModelProvider(this).get(PeriodsOfViewModel::class.java)
    }

    override fun onResume() {
        sharedViewModel.getPeriods().observe(viewLifecycleOwner, { render(it) })
        sharedViewModel.observableItem().observe(viewLifecycleOwner, { startEditFragment(it) })
        super.onResume()
    }

    private fun startEditFragment(it: Period?) {
        it?.let {
            val navController = findNavController()
            val action = PeriodsOfServiceFragmentDirections.actionPeriodsOfServiceFragmentToEditPeriodFragment()
            //action.period = it TODO Разобраться с информацией передаваемой в дочерние фрагменты
            navController.navigate(action)
        }
    }

    private fun render(periods: ArrayList<Period>) {
        periodsAdapter.setPeriods(periods)
        showAlertIfNull(periods.size)
    }

    //Отображает баннер в случае пустого списка
    private fun showAlertIfNull(size : Int) {
        if (size == 0) {
            binding.nothingShowBanner.visibility = View.VISIBLE
        } else binding.nothingShowBanner.visibility = View.GONE
    }

    private fun setListeners() {
        binding.addFloatingButton.setOnClickListener {
            val action = PeriodsOfServiceFragmentDirections.actionPeriodsOfServiceFragmentToEditPeriodFragment()
            //action.period = null TODO Разобраться с информацией передаваемой в дочерние фрагменты
            findNavController().navigate(action)
        }
    }

    private fun setRecyclerView() {
        periodsAdapter = PeriodsOfServiceRVAdapter(sharedViewModel)
        binding.periodsRecyclerView.apply {
            this.adapter = periodsAdapter
            val lm = LinearLayoutManager(context)
            lm.orientation = LinearLayoutManager.VERTICAL
            layoutManager = lm
        }
    }
}