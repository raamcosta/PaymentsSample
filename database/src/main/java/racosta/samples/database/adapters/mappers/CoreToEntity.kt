package racosta.samples.database.adapters.mappers

import racosta.samples.core.model.Payment
import racosta.samples.core.model.Refund
import racosta.samples.database.model.PaymentEntity
import racosta.samples.database.model.RefundEntity

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

fun Refund.toEntity() = RefundEntity(
    id,
    paymentId,
    amount,
    timestamp
)