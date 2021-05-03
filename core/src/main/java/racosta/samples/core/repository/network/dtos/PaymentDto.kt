package racosta.samples.core.repository.network.dtos

import kotlinx.serialization.Serializable

@Serializable
data class PaymentDto(
    val id: Int,
    val cardHolder: String,
    val cardNumber: Long,
    val cvv: Int,
    override val amount: Long,
    override val decimalPlaces: Int,
    val expDateMonth: Int,
    val expDateYear: Int,
    val timestamp: Long,
) : AmountHolderDto