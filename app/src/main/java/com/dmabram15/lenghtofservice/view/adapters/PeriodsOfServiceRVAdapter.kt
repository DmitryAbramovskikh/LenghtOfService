package com.dmabram15.lenghtofservice.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dmabram15.lenghtofservice.databinding.LengthServicePeriodItemBinding
import com.dmabram15.lenghtofservice.model.LongToDateConverter
import com.dmabram15.lenghtofservice.model.PeriodOfService

class PeriodsOfServiceRVAdapter : RecyclerView.Adapter<PeriodsOfServiceRVAdapter.PeriodsOfServiceViewHolder>() {

    private var periods = ArrayList<PeriodOfService>()
    private lateinit var binding : LengthServicePeriodItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeriodsOfServiceViewHolder {
        binding = LengthServicePeriodItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PeriodsOfServiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PeriodsOfServiceViewHolder, position: Int) {
        holder.binding.apply {
            beginPeriodDateTextView.text = LongToDateConverter.convert(periods[position].beginPeriod)
            endPeriodDateTextView.text = LongToDateConverter.convert(periods[position].endPeriod)
            lengthOfServiceItemTextView.text = LongToDateConverter.convertDifferent(
                ((periods[position].beginPeriod
                        - periods[position].endPeriod)
                        * periods[position].multiple).toLong()
            )
        }
    }

    override fun getItemCount(): Int {
        return periods.size
    }

    fun setPeriods(periods : ArrayList<PeriodOfService>) {
        this.periods.clear()
        this.periods.addAll(periods)
        notifyDataSetChanged()
    }

    class PeriodsOfServiceViewHolder(val binding : LengthServicePeriodItemBinding)
        : RecyclerView.ViewHolder(binding.root)
}