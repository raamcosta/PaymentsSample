package racosta.samples.core.model

import java.math.BigDecimal

data class PaymentWithRefunds(
    val payment: Payment,
    val refunds: List<Refund>
) {
    val refundedTotal: BigDecimal = calculateRefundedTotal()

    private fun calculateRefundedTotal(): BigDecimal {
        var total = BigDecimal(0)
        for (refund in refunds) {
            total = total.add(refund.amount)
        }
        return total
    }
}