package racosta.samples.core.logic

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import racosta.samples.core.commons.PaymentUserInputToModelMapper
import racosta.samples.core.model.Payment
import racosta.samples.core.repository.Repository

class SubmitNewPaymentUseCase internal constructor(
    private val repository: Repository,
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

        repository.addPayments(payment)
    }
}