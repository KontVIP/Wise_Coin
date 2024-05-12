package com.kontvip.wisecoin.domain.model

import androidx.annotation.StringRes
import com.kontvip.wisecoin.R
import java.util.Calendar
import java.util.concurrent.TimeUnit

sealed class TransactionPeriod(private val periodRes: Int) {

    abstract fun previous(): TransactionPeriod
    abstract fun next(): TransactionPeriod
    abstract fun from(): Long

    fun to(): Long = System.currentTimeMillis()

    fun display(display: Display) {
        display.displayPeriodName(periodRes)
    }

    data object Week : TransactionPeriod(R.string.week) {
        override fun previous(): TransactionPeriod = this
        override fun next(): TransactionPeriod = Month
        override fun from(): Long = to() - TimeUnit.DAYS.toMillis(7)
    }

    data object Month : TransactionPeriod(R.string.month) {
        override fun previous(): TransactionPeriod = Week
        override fun next(): TransactionPeriod = SixMonths
        override fun from(): Long {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MONTH, -1)
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            return calendar.timeInMillis
        }
    }

    data object SixMonths : TransactionPeriod(R.string.six_month) {
        override fun previous(): TransactionPeriod = Month
        override fun next(): TransactionPeriod = Year
        override fun from(): Long {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MONTH, -6)
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            return calendar.timeInMillis
        }
    }

    data object Year : TransactionPeriod(R.string.year) {
        override fun previous(): TransactionPeriod = SixMonths

        override fun next(): TransactionPeriod = this
        override fun from(): Long {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.YEAR, -1)
            calendar.set(Calendar.MONTH, Calendar.JANUARY)
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            return calendar.timeInMillis
        }
    }

    interface Display {
        fun displayPeriodName(@StringRes periodNameResource: Int)
    }
}