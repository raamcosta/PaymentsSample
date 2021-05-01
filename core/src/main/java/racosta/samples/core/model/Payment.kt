package racosta.samples.core.model

import java.math.BigDecimal

data class Payment(
    val id: Int = 0,
    val cardHolder: String,
    val cardNumber: Long,
    val cvv: Int,
    val amount: BigDecimal,
    val expDateMonth: Int,
    val expDateYear: Int,
    val timestamp: Long
)