package com.sector.scheduleapp.fragments.week

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sector.scheduleapp.databinding.ItemAdvantageBinding
import com.sector.scheduleapp.objects.Advantage

class AdvantagesAdapter(var advantagesList: List<Advantage>): RecyclerView.Adapter<AdvantagesAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemAdvantageBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(advantage: Advantage) = with(binding) {
            binding.tvSchedule.text = advantage.title
            binding.tvDescription.text = advantage.description
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemAdvantageBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            bind(advantagesList[position])
        }
    }

    override fun getItemCount(): Int {
        return advantagesList.size
    }
}