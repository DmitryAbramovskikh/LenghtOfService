package com.dmabram15.lenghtofservice.presentation.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dmabram15.lenghtofservice.databinding.LengthServicePeriodItemBinding
import com.dmabram15.lenghtofservice.presentation.viewModel.converters.DateConverter
import com.dmabram15.lenghtofservice.model.Period
import com.dmabram15.lenghtofservice.presentation.view.stringprovider.CalendarStringProviderImpl
import com.dmabram15.lenghtofservice.presentation.viewModel.listeners.OnChangeListListener

class PeriodsOfServiceRVAdapter(private val onChangeListListener : OnChangeListListener) : RecyclerView.Adapter<PeriodsOfServiceRVAdapter.PeriodsOfServiceViewHolder>() {

    private var periods = ArrayList<Period>()
    private lateinit var binding : LengthServicePeriodItemBinding
    private var dateConverter : DateConverter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeriodsOfServiceViewHolder {
        binding = LengthServicePeriodItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PeriodsOfServiceViewHolder(binding, 0)
    }

    override fun onBindViewHolder(holder: PeriodsOfServiceViewHolder, position: Int) {
        if (dateConverter == null) {
            dateConverter = DateConverter(CalendarStringProviderImpl(holder.binding.root.context))
        }
        holder.id = periods[position].id
        holder.binding.apply {
            beginPeriodDateTextView.text = dateConverter!!
                .convert(periods[position].beginPeriod)
            endPeriodDateTextView.text = dateConverter!!
                .convert(periods[position].endPeriod)
            lengthOfServiceItemTextView.text = dateConverter!!.convertDifferent(
                ((periods[position].endPeriod
                        - periods[position].beginPeriod)
                        * periods[position].multiple).toLong()
            )
            multipleCoefficientTextView.text = periods[position].multiple.toString()
            deleteItemButton.setOnClickListener {
                onChangeListListener.delete(periods[position])
            }
            editItemButton.setOnClickListener {
                onChangeListListener.edit(periods[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return periods.size
    }

    fun setPeriods(newPeriods : ArrayList<Period>) {
        val sortedNewPeriod = ArrayList<Period>(0)
        sortedNewPeriod.addAll(newPeriods.sortedBy { it.beginPeriod })
        val diffResult = DiffUtil.calculateDiff(PeriodsDiffCallback(periods, sortedNewPeriod))
        periods.let {
            periods.clear()
            periods = sortedNewPeriod
        }
        diffResult.dispatchUpdatesTo(this)
    }

    inner class PeriodsOfServiceViewHolder(val binding : LengthServicePeriodItemBinding, var id : Int)
        : RecyclerView.ViewHolder(binding.root)

    inner class PeriodsDiffCallback(
        private val oldPeriods : List<Period>,
        private val newPeriods : List<Period>
    ) : DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldPeriods.size
        }

        override fun getNewListSize(): Int {
            return newPeriods.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldPeriods[oldItemPosition].id == newPeriods[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldPeriods[oldItemPosition] == newPeriods[newItemPosition]
        }

    }
}