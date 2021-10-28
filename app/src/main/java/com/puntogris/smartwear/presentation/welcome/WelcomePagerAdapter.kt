package com.puntogris.smartwear.presentation.welcome

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.puntogris.smartwear.R
import com.puntogris.smartwear.domain.model.WelcomeItem

class WelcomePagerAdapter: RecyclerView.Adapter<WelcomeItemViewHolder>(){

    private var items = listOf(
        WelcomeItem(R.string.welcome_item_1, R.drawable.ic_search_illustration),
        WelcomeItem(R.string.welcome_item_2, R.drawable.ic_clothing_illustration),
        WelcomeItem(R.string.welcome_item_3, R.drawable.ic_amusment_park_ilustration)
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WelcomeItemViewHolder {
        return WelcomeItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: WelcomeItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}