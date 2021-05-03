package racosta.samples.core.repository.network.dtos

import kotlinx.serialization.Serializable

@Serializable
data class RefundDto(
    val id: Int,
    val paymentId: Int,
    override val amount: Long,
    override val decimalPlaces: Int,
    val timestamp: Long,
) : AmountHolderDto
