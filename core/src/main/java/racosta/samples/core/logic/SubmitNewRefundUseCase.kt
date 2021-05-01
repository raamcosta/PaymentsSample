package racosta.samples.core.logic

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import racosta.samples.commons.UiLogicFormatMapper.getAmountFromUI
import racosta.samples.core.commons.CurrentDateProvider
import racosta.samples.core.daoports.PaymentsDaoPort
import racosta.samples.core.daoports.RefundsDaoPort
import racosta.samples.core.model.Refund
import java.lang.RuntimeException

class SubmitNewRefundUseCase internal constructor(
    private val paymentsDao: PaymentsDaoPort,
    private val refundsDao: RefundsDaoPort,
    private val currentDateProvider: CurrentDateProvider
) {

    suspend fun newRefund(paymentId: Int, amount: String) = withContext(Dispatchers.IO) {
        val refundAmount = getAmountFromUI(amount)

        val paymentWithRefunds = paymentsDao.getPaymentWithRefunds(paymentId) ?: throw RuntimeException("Couldn't find payment for id $paymentId")
        if (paymentWithRefunds.refundedTotal.add(refundAmount) > paymentWithRefunds.payment.amount) {
            throw RuntimeException("UI is accepting invalid refund amount!")
        }

        refundsDao.insertAll(
            Refund(
                paymentId = paymentId,
                amount = refundAmount,
                timestamp = currentDateProvider.currentDate.timeInMillis
            )
        )
    }
}