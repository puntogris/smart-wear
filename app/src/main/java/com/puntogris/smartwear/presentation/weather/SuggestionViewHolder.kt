package com.puntogris.smartwear.presentation.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.puntogris.smartwear.databinding.SuggestionVhBinding
import com.puntogris.smartwear.domain.model.Location

class SuggestionViewHolder(private val binding: SuggestionVhBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(location: Location, clickListener: (Location) -> Unit, isLastItem: Boolean){
        binding.location = location
        binding.root.setOnClickListener {
            clickListener(location)
        }
        binding.divider.isVisible = !isLastItem

        binding.executePendingBindings()

    }

    companion object{
        fun from(parent: ViewGroup): SuggestionViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SuggestionVhBinding.inflate(layoutInflater, parent, false)
            return SuggestionViewHolder(binding)
        }
    }
}