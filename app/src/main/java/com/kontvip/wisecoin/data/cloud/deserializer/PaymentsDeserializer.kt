package com.kontvip.wisecoin.data.cloud.deserializer

import com.google.gson.*
import com.kontvip.wisecoin.data.MCCMapper
import com.kontvip.wisecoin.data.model.Payment
import com.kontvip.wisecoin.domain.model.Payments
import java.lang.reflect.Type

class PaymentsDeserializer(
    private val mccMapper: MCCMapper
) : JsonDeserializer<Payments> {

    companion object {
        private const val ID_FIELD = "id"
        private const val TIME_FIELD = "time"
        private const val DESCRIPTION_FIELD = "description"
        private const val MCC_FIELD = "mcc"
        private const val AMOUNT_FIELD = "amount"
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Payments {
        val array = json?.asJsonArray
        val payments = Payments()
        array?.forEach {
            val jsonObject = it.asJsonObject
            payments.add(
                Payment(
                    jsonObject.get(ID_FIELD).asString,
                    jsonObject.get(TIME_FIELD).asLong,
                    jsonObject.get(DESCRIPTION_FIELD).asString,
                    mccMapper.map(jsonObject.get(MCC_FIELD).asInt),
                    jsonObject.get(AMOUNT_FIELD).asInt
                )
            )
        }
        return payments
    }
}
