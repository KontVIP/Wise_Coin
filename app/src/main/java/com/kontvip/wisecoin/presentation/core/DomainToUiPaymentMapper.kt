package com.kontvip.wisecoin.presentation.core

import com.kontvip.wisecoin.domain.model.PaymentDomain
import com.kontvip.wisecoin.presentation.model.PaymentUi

class DomainToUiPaymentMapper : PaymentDomain.Mapper<PaymentUi> {
    override fun map(
        id: String, time: Long, description: String, category: String, amount: Int
    ): PaymentUi {
        return PaymentUi(id, time, description, category, amount)
    }

}