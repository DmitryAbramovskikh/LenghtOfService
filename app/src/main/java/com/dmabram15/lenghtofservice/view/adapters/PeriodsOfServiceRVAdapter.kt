package com.dmabram15.lenghtofservice.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dmabram15.lenghtofservice.databinding.LengthServicePeriodItemBinding
import com.dmabram15.lenghtofservice.model.DateConverter
import com.dmabram15.lenghtofservice.model.PeriodOfService

class PeriodsOfServiceRVAdapter (private val listener : OnPeriodDeleteClickListener) : RecyclerView.Adapter<PeriodsOfServiceRVAdapter.PeriodsOfServiceViewHolder>() {

    private var periods = ArrayList<PeriodOfService>()
    private lateinit var binding : LengthServicePeriodItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeriodsOfServiceViewHolder {
        binding = LengthServicePeriodItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PeriodsOfServiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PeriodsOfServiceViewHolder, position: Int) {
        holder.binding.apply {
            beginPeriodDateTextView.text = DateConverter
                .convert(periods[position].beginPeriod)
            endPeriodDateTextView.text = DateConverter
                .convert(periods[position].endPeriod)
            lengthOfServiceItemTextView.text = DateConverter.convertDifferent(
                ((periods[position].endPeriod
                        - periods[position].beginPeriod)
                        * periods[position].multiple).toLong()
            )
            multipleCoefficientTextView.text = periods[position].multiple.toString()
            periodDeleteButton.setOnClickListener{
                listener.delete(periods[position])
                periods.remove(periods[position])
                notifyDataSetChanged()
            }
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