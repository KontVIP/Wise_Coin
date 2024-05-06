package com.kontvip.wisecoin.data.cache

import com.kontvip.wisecoin.data.cache.database.PaymentDao
import com.kontvip.wisecoin.data.cache.database.PaymentEntity
import com.kontvip.wisecoin.domain.model.ClientInfo
import com.kontvip.wisecoin.data.model.PaymentData
import com.kontvip.wisecoin.domain.TransactionPeriod

interface CacheSource {

    fun saveMonobankToken(token: String)
    fun getMonobankToken(): String
    fun saveClientInfo(clientInfo: ClientInfo)
    fun fetchClientInfo(): ClientInfo
    fun clearClientInfo()
    fun saveIsSkippedMonobankAuth(isSkipped: Boolean)
    fun wasMonobankAuthSkipped(): Boolean
    suspend fun getAllPayments(period: TransactionPeriod): List<PaymentData>
    suspend fun savePayments(payments: List<PaymentData>)
    suspend fun savePayment(payment: PaymentData)

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

        override fun saveIsSkippedMonobankAuth(isSkipped: Boolean) {
            wiseCoinSharedPreferences.saveIsSkippedMonobankAuth(isSkipped)
        }

        override fun wasMonobankAuthSkipped(): Boolean {
            return wiseCoinSharedPreferences.getIsMonobankAuthSkipped()
        }

        override suspend fun savePayments(payments: List<PaymentData>) {
            paymentDao.insertPayments(payments.map {
                it.map(object : PaymentData.Mapper<PaymentEntity> {
                    override fun map(
                        id: String, time: Long, description: String, category: String, amount: Double, image: String
                    ): PaymentEntity {
                        return PaymentEntity(id, time, category, description, amount, image)
                    }
                })
            })
        }

        override suspend fun getAllPayments(period: TransactionPeriod): List<PaymentData> {
            return paymentDao.getPaymentsWithinPeriod(period.from(), period.to()).map {
                PaymentData(it.id, it.time, it.description, it.category, it.amount, it.image)
            }
        }

        override suspend fun savePayment(payment: PaymentData) {
            savePayments(listOf(payment))
        }
    }

}