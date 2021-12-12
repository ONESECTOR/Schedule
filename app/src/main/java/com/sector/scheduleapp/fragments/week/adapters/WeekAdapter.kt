package com.sector.scheduleapp.fragments.week.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sector.scheduleapp.R
import com.sector.scheduleapp.databinding.ItemDayBinding
import com.sector.scheduleapp.fragments.week.WeekFragmentDirections
import com.sector.scheduleapp.objects.Week

class WeekAdapter(var daysOfWeek: List<Week>): RecyclerView.Adapter<WeekAdapter.ViewHolder>() {

    inner class ViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = ItemDayBinding.bind(item)

        fun bind(week: Week) = with(binding) {
            tvDay.text = week.day
            tvNumberOfDay.text = week.numberOfDay
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            bind(daysOfWeek[position])

            binding.btnMore.setOnClickListener {
                val action = WeekFragmentDirections.actionWeekFragmentToDayDetailFragment(daysOfWeek[position])
                itemView.findNavController().navigate(action)
            }

            itemView.setOnClickListener {
                val action = WeekFragmentDirections.actionWeekFragmentToDayDetailFragment(daysOfWeek[position])
                itemView.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return daysOfWeek.size
    }
}