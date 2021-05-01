package racosta.samples.core.logic

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import racosta.samples.core.commons.PaymentUserInputToModelMapper
import racosta.samples.core.daoports.PaymentsDaoPort
import racosta.samples.core.model.Payment

class SubmitNewPaymentUseCase internal constructor(
    private val paymentsDao: PaymentsDaoPort,
    private val inputToModelMapper: PaymentUserInputToModelMapper
) {

    suspend fun newPayment(
        cardHolder: String,
        cardNumber: String,
        expDateMonth: String,
        expDateYear: String,
        cvv: String,
        amount: String
    ) = withContext(Dispatchers.IO) {

        val payment: Payment = inputToModelMapper.inputsToPayment(
            cardHolder,
            cardNumber,
            expDateMonth,
            expDateYear,
            cvv,
            amount
        )
            ?: throw RuntimeException("UI is accepting invalid payment!")

        paymentsDao.insertAll(payment)
    }
}