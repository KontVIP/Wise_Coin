package com.kontvip.wisecoin.data.cache.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PaymentDao {
    @Query("SELECT * FROM payments")
    fun getAllPayments(): List<PaymentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPayment(payment: PaymentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPayments(payments: List<PaymentEntity>)
}