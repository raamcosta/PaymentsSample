package racosta.samples.core.logic

import racosta.samples.commons.UiLogicFormatMapper.getAmountFromUI
import racosta.samples.core.commons.CurrentDateProvider
import racosta.samples.core.model.Refund
import racosta.samples.core.repository.Repository

class SubmitNewRefundUseCase internal constructor(
    private val repository: Repository,
    private val currentDateProvider: CurrentDateProvider
) {

    suspend fun newRefund(paymentId: Int, amount: String) {
        val refundAmount = getAmountFromUI(amount)

        val paymentWithRefunds = repository.getPaymentWithRefunds(paymentId)
            ?: throw RuntimeException("Couldn't find payment for id $paymentId")
        if (paymentWithRefunds.refundedTotal.add(refundAmount) > paymentWithRefunds.payment.amount) {
            throw RuntimeException("UI is accepting invalid refund amount!")
        }

        repository.addRefunds(
            Refund(
                paymentId = paymentId,
                amount = refundAmount,
                timestamp = currentDateProvider.currentDate.timeInMillis
            )
        )
    }
}