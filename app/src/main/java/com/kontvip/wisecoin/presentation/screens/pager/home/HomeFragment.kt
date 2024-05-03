package com.kontvip.wisecoin.presentation.screens.pager.home

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.kontvip.wisecoin.R
import com.kontvip.wisecoin.databinding.FragmentHomeBinding
import com.kontvip.wisecoin.domain.PercentageColorCreator
import com.kontvip.wisecoin.presentation.core.BaseFragment
import com.kontvip.wisecoin.presentation.model.CategoryItem
import com.kontvip.wisecoin.presentation.onClick
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList
import kotlin.math.roundToInt

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel by viewModels<HomeViewModel>()

    private var isExpensesSelected = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CategoryAdapter(emptyList(), isExpensesSelected)
        binding.categoriesRecyclerView.adapter = adapter
        binding.categoriesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.categoriesRecyclerView.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        )
        viewModel.fetchExpenses {
            displayChart(it)
            adapter.changeItems(it, isExpensesSelected)
        }

        binding.expensesTextView.onClick {
            if (!isExpensesSelected) {
                isExpensesSelected = true
                binding.expensesTextView.setBackgroundResource(R.drawable.transanction_type_selected_bg)
                binding.incomeTextView.setBackgroundResource(R.drawable.black_border)
                viewModel.fetchExpenses {
                    displayChart(it)
                    adapter.changeItems(it, isExpensesSelected)
                }
            }
        }

        binding.incomeTextView.onClick {
            if (isExpensesSelected) {
                isExpensesSelected = false
                binding.incomeTextView.setBackgroundResource(R.drawable.transanction_type_selected_bg)
                binding.expensesTextView.setBackgroundResource(R.drawable.black_border)
                viewModel.fetchIncomes {
                    displayChart(it)
                    adapter.changeItems(it, isExpensesSelected)
                }
            }
        }
    }

    private fun displayChart(categories: List<CategoryItem>) {
        val colorCreator = PercentageColorCreator.Default()
        // Calculate total expenses
        val total = categories.sumOf { if (isExpensesSelected) it.getTotalExpenses() else it.getTotalIncomes() }.toFloat()
        // Prepare data entries for the pie chart
        val entries = ArrayList<PieEntry>()

        val colors = ArrayList<Int>()

        categories.forEach { category ->
            val current = if (isExpensesSelected) category.getTotalExpenses() else category.getTotalIncomes()
            val percentage = (current / total) * 100

            colors.add(
                ColorUtils.setAlphaComponent(
                    colorCreator.getColorForString(category.toString()), (0.6 * 255).roundToInt()//colorCreator.getColorForPercentage(percentage, isExpensesSelected), (0.6 * 255).roundToInt())
                )
            )

            val pieEntry = PieEntry(percentage.toFloat())
            pieEntry.label = ""
            entries.add(pieEntry)
        }

        // Set up pie chart data
        val dataSet = PieDataSet(entries, "")
        dataSet.colors = colors // Set colors for the pie chart

        // Hide labels and description
        binding.pieChart.description.isEnabled = false
        binding.pieChart.legend.isEnabled = false
        binding.pieChart.setDrawEntryLabels(false)

        // Create pie chart data
        val data = PieData(dataSet)
        binding.pieChart.data = data

        // Refresh chart
        binding.pieChart.setDrawCenterText(true)
        binding.pieChart.centerText = (total / 100).toString()
        val green = ResourcesCompat.getColor(binding.root.resources, R.color.green,null)
        binding.pieChart.setCenterTextColor(if (isExpensesSelected) Color.RED else green)
        binding.pieChart.invalidate()
    }

}