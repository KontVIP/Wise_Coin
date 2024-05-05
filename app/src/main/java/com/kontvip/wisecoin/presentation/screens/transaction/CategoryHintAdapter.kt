package com.kontvip.wisecoin.presentation.screens.transaction

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kontvip.wisecoin.databinding.CategoryHintBinding
import com.kontvip.wisecoin.presentation.onClick

class CategoryHintAdapter(
    private var allItems: List<String>,
    private val listener: OnItemSelected
) : RecyclerView.Adapter<CategoryHintAdapter.ViewHolder>() {

    private var itemsToDisplay = listOf<String>()

    fun addNewItems(items: List<String>) {
        allItems = items
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filter(keyWord: String) {
        itemsToDisplay = allItems.filter { it.contains(keyWord) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CategoryHintBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemsToDisplay[position], listener)
    }

    override fun getItemCount(): Int = itemsToDisplay.size

    class ViewHolder(
        private val binding: CategoryHintBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: String, listener: OnItemSelected) {
            binding.categoryHintTextView.text = category
            binding.categoryHintTextView.onClick {
                listener.onSelected(category)
            }
        }
    }

    interface OnItemSelected {
        fun onSelected(category: String)
    }
}