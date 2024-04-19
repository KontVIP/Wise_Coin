package com.kontvip.wisecoin.presentation.screens.pager.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kontvip.wisecoin.databinding.CategoryItemBinding
import com.kontvip.wisecoin.presentation.model.CategoryItem

class CategoryAdapter(
    private val categories: List<CategoryItem>
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CategoryItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size

    inner class ViewHolder(
        private val binding: CategoryItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: CategoryItem) {
            category.display(binding)
            binding.percentageTextView.text = String.format("${((category.getTotalCost() / categories.sumOf { it.getTotalCost() }) * 100).toString().take(4)}")

        }
    }

}