package com.puntogris.smartwear.presentation.welcome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.puntogris.smartwear.databinding.IllustrationVhBinding
import com.puntogris.smartwear.domain.model.Illustration

class IllustrationViewHolder(private val binding: IllustrationVhBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(illustration: Illustration) {
        binding.imageView.setImageResource(illustration.image)
        binding.textView16.setText(illustration.tittle)
    }

    companion object {
        fun from(parent: ViewGroup): IllustrationViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = IllustrationVhBinding.inflate(layoutInflater, parent, false)
            return IllustrationViewHolder(binding)
        }
    }
}
