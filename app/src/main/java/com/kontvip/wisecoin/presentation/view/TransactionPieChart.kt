package com.kontvip.wisecoin.presentation.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.ColorUtils
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.kontvip.wisecoin.R
import com.kontvip.wisecoin.domain.PercentageColorCreator
import com.kontvip.wisecoin.presentation.model.CategoryItem
import com.kontvip.wisecoin.presentation.model.ReceiveName
import java.util.ArrayList
import kotlin.math.roundToInt

class TransactionPieChart @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : PieChart(context, attrs, defStyle) {

    private val colorCreator = PercentageColorCreator.Default()

    fun addCategoriesChartData(categories: List<CategoryItem>, isExpenses: Boolean) {
        val total = categories.sumOf { if (isExpenses) it.getTotalExpenses() else it.getTotalIncomes() }.toFloat()
        val entries = ArrayList<PieEntry>()
        val colors = ArrayList<Int>()

        categories.forEach { category ->
            val current = if (isExpenses) category.getTotalExpenses() else category.getTotalIncomes()
            val percentage = (current / total) * 100f

            category.provideName(object : ReceiveName {
                override fun onNameReceived(name: String) {
                    colors.add(
                        ColorUtils.setAlphaComponent(
                            colorCreator.getColorForString(name), (0.6 * 255).roundToInt()
                        )
                    )
                }
            })

            val pieEntry = PieEntry(percentage.toFloat())
            pieEntry.label = ""
            entries.add(pieEntry)
        }
        val dataSet = PieDataSet(entries, "")
        dataSet.colors = colors
        description.isEnabled = false
        legend.isEnabled = false
        setDrawEntryLabels(false)
        data = PieData(dataSet)
        setDrawCenterText(true)
        centerText = (total / 100).toString() + "$"
        val green = ResourcesCompat.getColor(resources, R.color.green,null)
        setCenterTextColor(if (isExpenses) Color.RED else green)
        invalidate()
    }
}