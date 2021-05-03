package racosta.samples.core.repository.network.mappers

import racosta.samples.core.model.Payment
import racosta.samples.core.model.Refund
import racosta.samples.core.repository.network.dtos.AmountHolderDto
import racosta.samples.core.repository.network.dtos.PaymentDto
import racosta.samples.core.repository.network.dtos.RefundDto
import java.math.BigDecimal
import kotlin.math.pow

fun PaymentDto.toCore() = Payment(
    id,
    cardHolder,
    cardNumber,
    cvv,
    coreAmount(),
    expDateMonth,
    expDateYear,
    timestamp
)

fun RefundDto.toCore() = Refund(
    id,
    paymentId,
    coreAmount(),
    timestamp
)

private fun AmountHolderDto.coreAmount() = BigDecimal(amount).divide(BigDecimal(10.0.pow(decimalPlaces)))