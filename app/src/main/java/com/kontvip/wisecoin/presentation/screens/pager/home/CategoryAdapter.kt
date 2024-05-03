package com.kontvip.wisecoin.presentation.screens.pager.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.kontvip.wisecoin.R
import com.kontvip.wisecoin.databinding.CategoryItemBinding
import com.kontvip.wisecoin.domain.PercentageColorCreator
import com.kontvip.wisecoin.presentation.model.CategoryItem

class CategoryAdapter(
    private var categories: List<CategoryItem>
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun changeItems(categories: List<CategoryItem>) {
        this.categories = categories
        notifyDataSetChanged()
    }

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

                override fun displayCost(cost: String) {
                    binding.costTextView.text = cost
                    if (cost.contains("-")) {
                        binding.costTextView.setTextColor(Color.RED)
                        binding.currencyTextView.setTextColor(Color.RED)
                    } else {
                        val green = ResourcesCompat.getColor(binding.root.resources, R.color.green,null)
                        binding.costTextView.setTextColor(green)
                        binding.currencyTextView.setTextColor(green)
                    }
                }
            })
            val percentage = (category.getTotalCost() / categories.sumOf { it.getTotalCost() }) * 100
            binding.percentageTextView.text = binding.root.context.getString(R.string.percentage, if (percentage == 100.0) "100" else percentage.toString().take(4))

            val colorCreator = PercentageColorCreator.Default()
            val circleColor = colorCreator.getColorForPercentage(percentage, category.getTotalCost() < 0)
            val resources = binding.root.resources
            binding.percentageTextView.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    if (colorCreator.isColorDark(circleColor)) R.color.white else R.color.black,
                    null
                )
            )
            binding.percentageCardView.setCardBackgroundColor(circleColor)
        }
    }

}