package com.kontvip.wisecoin.presentation.screens.pager.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kontvip.wisecoin.R
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
            category.display(object : CategoryItem.Display {
                override fun displayCategoryName(name: String) {
                    binding.categoryNameTextView.text = name
                }

                override fun displayImage(imageUrl: String) {
                    if (imageUrl.isNotBlank()) {
                        Glide.with(binding.categoryIconImageView).load(imageUrl)
                            .into(binding.categoryIconImageView)
                    } else {
                        //todo: handle the icon
                        binding.categoryIconImageView.setImageResource(R.drawable.ic_home)
                    }
                }

                override fun displayCost(cost: String) {
                    binding.costTextView.text = cost
                }

            })
            val percentage = ((category.getTotalCost() / categories.sumOf { it.getTotalCost() }) * 100).toString()
            binding.percentageTextView.text = binding.root.context.getString(R.string.percentage, percentage.take(4))

        }
    }

}