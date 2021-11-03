package com.sector.scheduleapp.fragments.week

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sector.scheduleapp.R
import com.sector.scheduleapp.databinding.ItemDayBinding
import com.sector.scheduleapp.objects.Day

class WeekAdapter(var days: List<Day>): RecyclerView.Adapter<WeekAdapter.ViewHolder>() {

    inner class ViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = ItemDayBinding.bind(item)

        fun bind(day: Day) = with(binding) {
            tvDay.text = day.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(days[position])
    }

    override fun getItemCount(): Int {
        return days.size
    }
}