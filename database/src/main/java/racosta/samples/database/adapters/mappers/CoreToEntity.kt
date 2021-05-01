package racosta.samples.database.adapters.mappers

import racosta.samples.core.model.Payment
import racosta.samples.database.model.PaymentEntity

fun Payment.toEntity() = PaymentEntity(
    id,
    cardHolder,
    cardNumber,
    cvv,
    amount,
    expDateMonth,
    expDateYear,
    timestamp
)