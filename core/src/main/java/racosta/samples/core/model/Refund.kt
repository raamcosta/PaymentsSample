package racosta.samples.core.model

import java.math.BigDecimal

data class Refund(
    val id: Int,
    val paymentId: Int,
    val amount: BigDecimal,
    val timestamp: Long
)