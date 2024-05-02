package com.kontvip.wisecoin.data.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PaymentEntity::class], version = 2)
abstract class PaymentDatabase : RoomDatabase() {
    abstract fun paymentDao(): PaymentDao
}