package racosta.samples.database.adapters.mappers

import racosta.samples.core.model.Payment
import racosta.samples.core.model.PaymentWithRefunds
import racosta.samples.core.model.Refund
import racosta.samples.database.model.PaymentEntity
import racosta.samples.database.model.PaymentWithRefundsEntity
import racosta.samples.database.model.RefundEntity

fun PaymentEntity.toCore() = Payment(
    id,
    cardHolder,
    cardNumber,
    cvv,
    amount,
    expDateMonth,
    expDateYear,
    timestamp
)

fun RefundEntity.toCore() = Refund(
    id,
    paymentId,
    amount,
    timestamp
)

fun PaymentWithRefundsEntity.toCore() = PaymentWithRefunds(
    payment.toCore(),
    refunds.map { it.toCore() }
)