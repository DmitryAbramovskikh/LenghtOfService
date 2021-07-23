package com.dmabram15.lenghtofservice.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dmabram15.lenghtofservice.data.repository.RoomRepository
import com.dmabram15.lenghtofservice.databinding.PeriodsOfFragmentBinding
import com.dmabram15.lenghtofservice.model.Period
import com.dmabram15.lenghtofservice.view.adapters.PeriodsOfServiceRVAdapter
import com.dmabram15.lenghtofservice.viewModel.listeners.OnChangeListListener
import com.dmabram15.lenghtofservice.viewModel.viewmodel.PeriodsOfViewModel

class PeriodsOfServiceFragment : Fragment(), OnChangeListListener {

    private lateinit var periodsAdapter: PeriodsOfServiceRVAdapter
    private lateinit var binding: PeriodsOfFragmentBinding

    private lateinit var viewModel: PeriodsOfViewModel

    //using dagger
    private val repository = RoomRepository.getInstance()

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
        viewModel = ViewModelProvider(this).get(PeriodsOfViewModel::class.java)
        viewModel.periodsData.observe(viewLifecycleOwner, {render(it)})
    }

    override fun onResume() {
        viewModel.fetchPeriods(repository)
        super.onResume()
    }

    private fun render(periods: List<Period>?) {
        periods?.let{
            val array = arrayListOf<Period>()
            array.addAll(periods)
            periodsAdapter.setPeriods(array)
            showAlertIfNull(periods.size)
        }
    }

    //Отображает баннер в случае пустого списка
    private fun showAlertIfNull(size : Int) {
        if (size == 0) {
            binding.nothingShowBanner.visibility = View.VISIBLE
        } else binding.nothingShowBanner.visibility = View.GONE
    }

    private fun setListeners() {
        binding.addFloatingButton.setOnClickListener {
            startEditFragment(null)
        }
    }

    private fun setRecyclerView() {
        periodsAdapter = PeriodsOfServiceRVAdapter(this)
        binding.periodsRecyclerView.apply {
            this.adapter = periodsAdapter
            val lm = LinearLayoutManager(context)
            lm.orientation = LinearLayoutManager.VERTICAL
            layoutManager = lm
        }
    }

    private fun startEditFragment(period: Period?) {
        period.let {
            val action = PeriodsOfServiceFragmentDirections
                .actionPeriodsOfServiceFragmentToEditPeriodFragment()
            action.periodId = viewModel.getPeriodIdFromPeriod(it)
            findNavController().navigate(action)
        }
    }

    override fun delete(period: Period) {
        viewModel.deletePeriod(repository, period)
    }

    override fun edit(period: Period) {
        startEditFragment(period)
    }

}