package com.kontvip.wisecoin.domain

import android.graphics.Color

interface PercentageColorCreator {

    fun getColorForPercentage(percentage: Double, invertPercentage: Boolean = false): Int
    fun isColorDark(color: Int): Boolean

    class Default : PercentageColorCreator {

        override fun getColorForPercentage(percentage: Double, invertPercentage: Boolean): Int {
            val finalPercentage = if (invertPercentage) 100 - percentage else percentage
            val green = Color.parseColor("#00FF00")
            val red = Color.parseColor("#FF0000")

            val clampedValue = finalPercentage.coerceIn(0.0, 100.0)

            val proportion = clampedValue / 100.0

            val adjustedProportion = if (proportion <= 0.5) {
                proportion * 2
            } else {
                0.5 + (proportion - 0.5) * 0.5
            }

            val r = (Color.red(green) * adjustedProportion + Color.red(red) * (1 - adjustedProportion)).toInt()
            val g = (Color.green(green) * adjustedProportion + Color.green(red) * (1 - adjustedProportion)).toInt()
            val b = (Color.blue(green) * adjustedProportion + Color.blue(red) * (1 - adjustedProportion)).toInt()

            return Color.rgb(r, g, b)
        }

        override fun isColorDark(color: Int): Boolean {
            val darkness = 1 - (0.299 * Color.red(color) +
                    0.587 * Color.green(color) +
                    0.114 * Color.blue(color)) / 255
            return darkness >= 0.5
        }

    }
}