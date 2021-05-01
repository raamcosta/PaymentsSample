package racosta.samples.payments.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import racosta.samples.core.commons.PaymentUserInputValidator
import racosta.samples.core.logic.SubmitNewPaymentUseCase
import racosta.samples.payments.commons.ActionValues
import racosta.samples.payments.commons.NavigationAction
import racosta.samples.payments.commons.Tagged
import racosta.samples.payments.ui.screens.NewPaymentFragmentDirections
import racosta.samples.payments.ui.screens.base.BaseViewModel

/**
 * ViewModel for the NewPayment screen.
 * Holds UI state and receives user events to mutate that state.
 */
class NewPaymentViewModel(
    private val inputsValidator: PaymentUserInputValidator,
    private val submitNewPaymentUseCase: SubmitNewPaymentUseCase
) : BaseViewModel(), Tagged {

    private var expDateMonth = ""
    private var expDateYear = ""
    private var cardNumber = ""
    private var cvv = ""
    private var amount = ""
    private var cardHolder = ""

    private val showExpDateInvalid = MutableLiveData(false)
    private val showCvvInvalid = MutableLiveData(false)
    private val showCardNumberInvalid = MutableLiveData(false)
    private val showAmountInvalid = MutableLiveData(false)
    private val confirmPaymentEnabled = MutableLiveData(false)

    //region user events
    fun onDateChanged(
        month: String,
        year: String
    ) {
        expDateMonth = month
        expDateYear = year

        val isValid: Boolean
        if (month.isEmpty() && year.isEmpty()) {
            isValid = false
            showExpDateInvalid.setValue(false)
        } else {
            isValid = inputsValidator.isDateValid(month, year)
            showExpDateInvalid.setValue(!isValid)
        }

        checkConfirmPaymentEnabled(isValid)
    }

    fun onCvvChanged(cvv: String) {
        this.cvv = cvv

        val isValid: Boolean
        if (cvv.isEmpty()) {
            isValid = false
            showCvvInvalid.setValue(false)
        } else {
            isValid = inputsValidator.isCvvValid(cvv)
            showCvvInvalid.setValue(!isValid)
        }

        checkConfirmPaymentEnabled(isValid)
    }

    fun onCardNumberChanged(cardNumber: String) {
        this.cardNumber = cardNumber

        val isValid: Boolean
        if (cardNumber.isEmpty()) {
            isValid = false
            showCardNumberInvalid.setValue(false)
        } else {
            isValid = inputsValidator.isCardNumberValid(cardNumber)
            showCardNumberInvalid.setValue(!isValid)
        }

        checkConfirmPaymentEnabled(isValid)
    }

    fun onAmountChanged(amount: String) {
        this.amount = amount

        val isValid: Boolean
        if (amount.isEmpty()) {
            isValid = false
            showAmountInvalid.setValue(false)
        } else {
            isValid = inputsValidator.isAmountValid(amount)
            showAmountInvalid.setValue(!isValid)
        }

        checkConfirmPaymentEnabled(isValid)
    }

    fun onCardHolderChanged(cardHolderName: String) {
        cardHolder = cardHolderName
    }

    fun onConfirmClicked() = viewModelScope.launch {
        if (!areAllInputValuesValid()) {
            return@launch
        }

        submitNewPaymentUseCase.newPayment(
            cardHolder,
            cardNumber,
            expDateMonth,
            expDateYear,
            cvv,
            amount
        )

        navigate(
            NavigationAction(
                NewPaymentFragmentDirections.actionToPaymentsHistoryFragment(),
                ActionValues.MAKE_PAYMENT
            )
        )
    }

    //endregion
    //region exposed UI State
    fun showExpDateInvalid(): LiveData<Boolean> {
        return showExpDateInvalid
    }

    fun showCvvInvalid(): LiveData<Boolean> {
        return showCvvInvalid
    }

    fun showCardNumberInvalid(): LiveData<Boolean> {
        return showCardNumberInvalid
    }

    fun showAmountInvalid(): LiveData<Boolean> {
        return showAmountInvalid
    }

    fun confirmPaymentEnabled(): LiveData<Boolean> {
        return confirmPaymentEnabled
    }

    //endregion

    //region internal logic
    private fun checkConfirmPaymentEnabled(isValid: Boolean) {
        if (isValid == confirmPaymentEnabled.value) {
            return
        }
        confirmPaymentEnabled.value = areAllInputValuesValid()
    }

    private fun areAllInputValuesValid(): Boolean {
        return inputsValidator.isDateValid(expDateMonth, expDateYear)
                && inputsValidator.isAmountValid(amount)
                && inputsValidator.isCvvValid(cvv)
                && inputsValidator.isCardNumberValid(cardNumber)
    }

    //endregion
}