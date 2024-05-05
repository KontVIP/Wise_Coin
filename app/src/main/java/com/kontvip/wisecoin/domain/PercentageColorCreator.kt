package com.kontvip.wisecoin.domain

import android.graphics.Color
import kotlin.random.Random

interface PercentageColorCreator {

    fun getColorForPercentage(percentage: Double, invertPercentage: Boolean = false): Int
    fun isColorDark(color: Int): Boolean
    fun getColorForString(input: String): Int

    class Default : PercentageColorCreator {

        companion object {
            private val colorsForStrings = mutableMapOf<String, Int>()
        }

        override fun getColorForPercentage(percentage: Double, invertPercentage: Boolean): Int {
            val finalPercentage = if (invertPercentage) 100 - percentage else percentage
            val green = Color.parseColor("#00FF00")
            val red = Color.parseColor("#FF0000")

            val clampedValue = finalPercentage.coerceIn(0.0, 100.0)

            val proportion = clampedValue / 100.0

            val r = (Color.red(green) * proportion + Color.red(red) * (1 - proportion)).toInt()
            val g = (Color.green(green) * proportion + Color.green(red) * (1 - proportion)).toInt()
            val b = (Color.blue(green) * proportion + Color.blue(red) * (1 - proportion)).toInt()

            return Color.rgb(r, g, b)
        }


        override fun getColorForString(input: String): Int {
            return colorsForStrings.getOrPut(input) {
                val hash = input.hashCode()
                val hue = (Random(hash).nextInt(0, Int.MAX_VALUE) * 3.6f) % 360f
                Color.HSVToColor(floatArrayOf(hue, 1.0f, 1.0f))
            }
        }

        override fun isColorDark(color: Int): Boolean {
            val darkness = 1 - (0.299 * Color.red(color) +
                    0.587 * Color.green(color) +
                    0.114 * Color.blue(color)) / 255
            return darkness >= 0.5
        }

    }
}