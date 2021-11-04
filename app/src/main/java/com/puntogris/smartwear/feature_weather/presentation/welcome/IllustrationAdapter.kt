package com.puntogris.smartwear.feature_weather.presentation.welcome

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.puntogris.smartwear.R
import com.puntogris.smartwear.feature_weather.domain.model.Illustration

class IllustrationAdapter: RecyclerView.Adapter<IllustrationViewHolder>(){

    private var items = listOf(
        Illustration(R.string.welcome_item_1, R.drawable.ic_search_illustration),
        Illustration(R.string.welcome_item_2, R.drawable.ic_clothing_illustration),
        Illustration(R.string.welcome_item_3, R.drawable.ic_amusment_park_ilustration)
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IllustrationViewHolder {
        return IllustrationViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: IllustrationViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}