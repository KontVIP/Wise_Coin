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
import com.kontvip.wisecoin.domain.model.Currency
import com.kontvip.wisecoin.presentation.model.CategoryItem
import com.kontvip.wisecoin.presentation.core.onClick
import java.text.DecimalFormat

class CategoryAdapter(
    private var categories: List<CategoryItem>,
    private var isExpenses: Boolean,
    private val currency: Currency,
    private val onCategoryClick: OnCategoryClick
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun changeItems(categories: List<CategoryItem>, isExpenses: Boolean) {
        this.categories = categories
        this.isExpenses = isExpenses
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CategoryItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, onCategoryClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size

    inner class ViewHolder(
        private val binding: CategoryItemBinding,
        private val onCategoryClick: OnCategoryClick
    ) : RecyclerView.ViewHolder(binding.root), Currency.DisplayCurrency {

        fun bind(category: CategoryItem) {
            currency.display(this)
            val colorCreator = PercentageColorCreator.Default()
            category.display(object : CategoryItem.Display {
                override fun displayCategoryName(name: String) {
                    binding.categoryNameTextView.text = name
                    val circleColor = colorCreator.getColorForString(name)
                    binding.percentageTextView.setTextColor(
                        if (colorCreator.isColorDark(circleColor)) Color.WHITE else Color.BLACK
                    )
                    binding.percentageCardView.setCardBackgroundColor(circleColor)
                }

                override fun displayCost(cost: String) {
                    binding.costTextView.text = cost
                    if (isExpenses) {
                        binding.costTextView.setTextColor(Color.RED)
                        binding.currencyTextView.setTextColor(Color.RED)
                    } else {
                        val green = ResourcesCompat.getColor(binding.root.resources, R.color.green,null)
                        binding.costTextView.setTextColor(green)
                        binding.currencyTextView.setTextColor(green)
                    }
                }
            })

            val percentage = (if (isExpenses) {
                (category.getTotalExpenses() / categories.sumOf { it.getTotalExpenses() })
            } else {
                (category.getTotalIncomes() / categories.sumOf { it.getTotalIncomes() })
            })  * 100

            val roundedPercentage = DecimalFormat("#.#").format(percentage.toFloat())
            binding.percentageTextView.text = binding.root.context.getString(R.string.percentage, roundedPercentage.toString())
            binding.root.onClick {
                onCategoryClick.onClick(category)
            }
        }

        override fun displayCurrency(currencyRes: Int, signRes: Int) {
            binding.currencyTextView.setText(signRes)
        }
    }

}