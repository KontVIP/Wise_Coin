package com.kontvip.wisecoin.data.cache

import com.kontvip.wisecoin.data.cache.database.PaymentDao
import com.kontvip.wisecoin.data.cache.database.PaymentEntity
import com.kontvip.wisecoin.data.model.ClientInfo
import com.kontvip.wisecoin.data.model.PaymentData

interface CacheSource {

    fun saveMonobankToken(token: String)
    fun getMonobankToken(): String
    fun saveClientInfo(clientInfo: ClientInfo)
    fun fetchClientInfo(): ClientInfo
    fun clearClientInfo()
    suspend fun savePayments(payments: List<PaymentData>)
    suspend fun getAllPayments(): List<PaymentData>

    class Default(
        private val wiseCoinSharedPreferences: WiseCoinSharedPreferences,
        private val paymentDao: PaymentDao
    ) : CacheSource {

        override fun saveMonobankToken(token: String) {
            wiseCoinSharedPreferences.saveMonobankToken(token)
        }

        override fun getMonobankToken(): String {
            return wiseCoinSharedPreferences.getMonobankToken()
        }

        override fun saveClientInfo(clientInfo: ClientInfo) {
            clientInfo.map(object : ClientInfo.Mapper<Unit> {
                override fun map(id: String, name: String) {
                    wiseCoinSharedPreferences.saveClientId(id)
                    wiseCoinSharedPreferences.saveClientName(name)
                }
            })
        }

        override fun fetchClientInfo(): ClientInfo {
            return ClientInfo(
                wiseCoinSharedPreferences.getClientId(), wiseCoinSharedPreferences.getClientName()
            )
        }

        override fun clearClientInfo() {
            wiseCoinSharedPreferences.saveClientId("")
            wiseCoinSharedPreferences.saveClientName("")
        }

        override suspend fun savePayments(payments: List<PaymentData>) {
            paymentDao.insertPayments(payments.map {
                it.map(object : PaymentData.Mapper<PaymentEntity> {
                    override fun map(
                        id: String, time: Long, description: String, category: String, amount: Int, image: String
                    ): PaymentEntity {
                        return PaymentEntity(id, time, category, description, amount, image)
                    }
                })
            })
        }

        override suspend fun getAllPayments(): List<PaymentData> {
            return paymentDao.getAllPayments().map {
                PaymentData(it.id, it.time, it.description, it.category, it.amount, it.image)
            }
        }
    }

}