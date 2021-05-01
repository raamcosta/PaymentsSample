package racosta.samples.core.commons

import racosta.samples.commons.UiLogicFormatMapper
import racosta.samples.core.model.Payment

internal class PaymentUserInputToModelMapper(
    private val inputValidator: PaymentUserInputValidator,
    private val currentDateProvider: CurrentDateProvider
) {

    fun inputsToPayment(
        cardHolder: String,
        cardNumber: String,
        expDateMonth: String,
        expDateYear: String,
        cvv: String,
        amount: String
    ): Payment? {
        return if (!isInputValid(cardNumber, expDateMonth, expDateYear, cvv, amount)) {
            null
        } else {
            Payment(
                cardHolder = cardHolder,
                cardNumber = UiLogicFormatMapper.getCardNumberFromUI(cardNumber),
                cvv = cvv.toInt(),
                amount = UiLogicFormatMapper.getAmountFromUI(amount),
                expDateMonth = expDateMonth.toInt(),
                expDateYear = expDateYear.toInt(),
                timestamp = currentDateProvider.currentDate.timeInMillis
            )
        }
    }

    private fun isInputValid(
        cardNumber: String,
        expDateMonth: String,
        expDateYear: String,
        cvv: String,
        amount: String
    ) = when {
        !inputValidator.isCardNumberValid(cardNumber) -> false
        !inputValidator.isAmountValid(amount) -> false
        !inputValidator.isCvvValid(cvv) -> false
        !inputValidator.isDateValid(expDateMonth, expDateYear) -> false
        else -> true
    }
}