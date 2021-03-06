package racosta.samples.core.model

import java.math.BigDecimal

data class Refund(
    val id: Int = 0,
    val paymentId: Int,
    val amount: BigDecimal,
    val timestamp: Long
)