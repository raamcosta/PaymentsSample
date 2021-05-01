package racosta.samples.database.model

import androidx.room.*
import java.math.BigDecimal

data class PaymentWithRefundsEntity(
    @Embedded val payment: PaymentEntity,
    @Relation(parentColumn = "id", entityColumn = "paymentId") val refunds: List<RefundEntity>
) {
    @Ignore
    val refundedTotal: BigDecimal

    init {
        refundedTotal = calculateRefundedTotal()
    }

    private fun calculateRefundedTotal(): BigDecimal {
        var total = BigDecimal(0)
        for (refund in refunds) {
            total = total.add(refund.amount)
        }
        return total
    }
}