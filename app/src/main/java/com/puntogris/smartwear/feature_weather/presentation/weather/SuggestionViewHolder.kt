package com.puntogris.smartwear.feature_weather.presentation.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.puntogris.smartwear.databinding.SuggestionVhBinding
import com.puntogris.smartwear.feature_weather.domain.model.Location

class SuggestionViewHolder(private val binding: SuggestionVhBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(location: Location, clickListener: (Location) -> Unit, isLastItem: Boolean) {
        with(binding) {
            this.location = location
            root.setOnClickListener {
                clickListener(location)
            }
            divider.isVisible = !isLastItem
            executePendingBindings()
        }
    }

    companion object {
        fun from(parent: ViewGroup): SuggestionViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SuggestionVhBinding.inflate(layoutInflater, parent, false)
            return SuggestionViewHolder(binding)
        }
    }
}