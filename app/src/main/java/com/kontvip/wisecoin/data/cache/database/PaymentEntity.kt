package com.kontvip.wisecoin.data.cache.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "payments")
data class PaymentEntity(
    @PrimaryKey val id: String,
    val time: Long,
    val category: String,
    val description: String,
    val amount: Int
)