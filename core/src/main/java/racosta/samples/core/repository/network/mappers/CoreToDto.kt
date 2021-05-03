package racosta.samples.core.repository.network.mappers

import racosta.samples.core.model.Payment
import racosta.samples.core.model.Refund
import racosta.samples.core.repository.network.dtos.PaymentDto
import racosta.samples.core.repository.network.dtos.RefundDto
import java.math.BigDecimal

fun Refund.toDto() = RefundDto(
    id,
    paymentId,
    amount.toDto(),
    2,
    timestamp
)


fun Payment.toDto() = PaymentDto(
    id,
    cardHolder,
    cardNumber,
    cvv,
    amount.toDto(),
    2,
    expDateMonth,
    expDateYear,
    timestamp
)

fun BigDecimal.toDto() = multiply(BigDecimal(100)).toLong()