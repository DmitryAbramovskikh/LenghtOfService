package com.dmabram15.lenghtofservice.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dmabram15.lenghtofservice.databinding.LengthServicePeriodItemBinding
import com.dmabram15.lenghtofservice.model.LongToDateConverter
import com.dmabram15.lenghtofservice.model.PeriodOfService
import com.dmabram15.lenghtofservice.view.interfaces.OnChangeListListener

class PeriodsOfServiceRVAdapter(private val onChangeListListener : OnChangeListListener) : RecyclerView.Adapter<PeriodsOfServiceRVAdapter.PeriodsOfServiceViewHolder>() {

    private var periods = ArrayList<PeriodOfService>()
    private lateinit var binding : LengthServicePeriodItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeriodsOfServiceViewHolder {
        binding = LengthServicePeriodItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PeriodsOfServiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PeriodsOfServiceViewHolder, position: Int) {
        holder.binding.apply {
            beginPeriodDateTextView.text = LongToDateConverter
                .convert(periods[position].beginPeriod)
            endPeriodDateTextView.text = LongToDateConverter
                .convert(periods[position].endPeriod)
            lengthOfServiceItemTextView.text = LongToDateConverter.convertDifferent(
                ((periods[position].endPeriod
                        - periods[position].beginPeriod)
                        * periods[position].multiple).toLong()
            )
            multipleCoefficientTextView.text = periods[position].multiple.toString()
            deleteItemButton.setOnClickListener {
                onChangeListListener.delete(position)
            }
            editItemButton.setOnClickListener {
                onChangeListListener.edit(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return periods.size
    }

    fun setPeriods(newPeriods : ArrayList<PeriodOfService>) {
        val diffResult = DiffUtil.calculateDiff(PeriodsDiffCallback(periods, newPeriods))
        periods = newPeriods
        diffResult.dispatchUpdatesTo(this)
    }

    inner class PeriodsOfServiceViewHolder(val binding : LengthServicePeriodItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    inner class PeriodsDiffCallback(
        private val oldPeriods : ArrayList<PeriodOfService>,
        private val newPeriods : ArrayList<PeriodOfService>
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