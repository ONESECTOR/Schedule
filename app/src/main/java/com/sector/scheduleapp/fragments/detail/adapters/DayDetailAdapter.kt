package com.sector.scheduleapp.fragments.detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sector.scheduleapp.databinding.ItemSubjectDetailBinding
import com.sector.scheduleapp.objects.Day

class DayDetailAdapter: ListAdapter<Day, DayDetailAdapter.ViewHolder>(ItemComparator()) {

    class ViewHolder(private val binding: ItemSubjectDetailBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(day: Day) = with(binding) {
            tvTitle.text = if(day.group.isEmpty()) day.subject else "${day.subject} (${day.group})"
            tvInfo.text = if(day.teacher.isEmpty()) day.type else "${day.teacher}, ${day.type}"
            tvTime.text = day.time
            tvAud.text = day.classroom
            tvNumberOfSubject.text = (adapterPosition + 1).toString()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemSubjectDetailBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            bind(getItem(position))
        }
    }

    class ItemComparator: DiffUtil.ItemCallback<Day>() {
        override fun areItemsTheSame(oldItem: Day, newItem: Day): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Day, newItem: Day): Boolean {
            return oldItem == newItem
        }
    }
}