package com.kontvip.wisecoin.data.cloud.deserializer

import com.google.gson.*
import com.kontvip.wisecoin.data.mapper.MccToCategoryMapper
import com.kontvip.wisecoin.data.model.PaymentData
import java.lang.reflect.Type

class PaymentsDeserializer(
    private val mccToCategoryMapper: MccToCategoryMapper
) : JsonDeserializer<Array<PaymentData>> {

    companion object {
        private const val ID_FIELD = "id"
        private const val TIME_FIELD = "time"
        private const val DESCRIPTION_FIELD = "description"
        private const val MCC_FIELD = "mcc"
        private const val AMOUNT_FIELD = "amount"
        private const val MONOBANK_DEFAULT_IMAGE_URL = "https://miro.medium.com/v2/resize:fit:2400/1*PxKUZIus4lJELdfYKYJuyA.png"
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Array<PaymentData> {
        val array = json?.asJsonArray
        val payments = mutableListOf<PaymentData>()
        array?.forEach {
            val jsonObject = it.asJsonObject
            payments.add(
                PaymentData(
                    jsonObject.get(ID_FIELD).asString,
                    jsonObject.get(TIME_FIELD).asLong  * 1000,
                    jsonObject.get(DESCRIPTION_FIELD).asString,
                    mccToCategoryMapper.map(jsonObject.get(MCC_FIELD).asInt),
                    jsonObject.get(AMOUNT_FIELD).asDouble,
                    MONOBANK_DEFAULT_IMAGE_URL
                )
            )
        }
        return payments.toTypedArray()
    }
}
