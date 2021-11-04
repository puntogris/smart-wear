package com.puntogris.smartwear.feature_weather.presentation.weather

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.puntogris.smartwear.feature_weather.domain.model.Location

class SuggestionsAdapter(
    private val clickListener: (Location) -> Unit
): RecyclerView.Adapter<SuggestionViewHolder>() {

    private var suggestions = listOf<Location>()

    override fun getItemCount() = suggestions.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionViewHolder {
        return SuggestionViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
        holder.bind(suggestions[position], clickListener, position.inc() == itemCount)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSuggestions(suggestions: List<Location>){
        this.suggestions = suggestions
        notifyDataSetChanged()
    }
}