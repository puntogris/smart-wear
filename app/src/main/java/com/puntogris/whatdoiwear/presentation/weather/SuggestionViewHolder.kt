package com.puntogris.whatdoiwear.presentation.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.puntogris.whatdoiwear.databinding.SuggestionVhBinding
import com.puntogris.whatdoiwear.domain.model.Location

class SuggestionViewHolder(private val binding: SuggestionVhBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(location: Location, clickListener: (Location) -> Unit){
        binding.location = location
        binding.root.setOnClickListener {
            clickListener(location)
        }
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