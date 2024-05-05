package com.kontvip.wisecoin.data.cache.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PaymentDao {
    @Query("SELECT * FROM payments ORDER by time DESC")
    fun getAllPayments(): List<PaymentEntity>

    @Query("SELECT * FROM payments WHERE time BETWEEN :from AND :to ORDER BY time DESC")
    fun getPaymentsWithinPeriod(from: Long, to: Long): List<PaymentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPayment(payment: PaymentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPayments(payments: List<PaymentEntity>)
}