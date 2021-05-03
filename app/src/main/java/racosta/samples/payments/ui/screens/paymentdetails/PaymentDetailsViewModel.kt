package racosta.samples.payments.ui.screens.paymentdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import racosta.samples.commons.UiLogicFormatMapper
import racosta.samples.core.commons.PaymentUserInputValidator
import racosta.samples.core.logic.ObservePaymentWithRefundsForIdUseCase
import racosta.samples.core.logic.SubmitNewRefundUseCase
import racosta.samples.core.model.PaymentWithRefunds
import racosta.samples.payments.ui.screens.base.BaseViewModel
import java.math.BigDecimal

class PaymentDetailsViewModel(
    private val paymentId: Int,
    private val observePaymentWithRefundsForIdUseCase: ObservePaymentWithRefundsForIdUseCase,
    private val inputValidator: PaymentUserInputValidator,
    private val submitNewRefundUseCase: SubmitNewRefundUseCase
) : BaseViewModel() {

    private var internalPaymentWithRefunds: PaymentWithRefunds? = null
    private var interalRefundedTotal = BigDecimal(0)

    private val _paymentWithRefunds: MutableLiveData<PaymentWithRefunds?> = MutableLiveData<PaymentWithRefunds?>(internalPaymentWithRefunds)
    private val _refundedTotal = MutableLiveData(interalRefundedTotal)
    private val _totalNotRefunded = MutableLiveData<BigDecimal?>(null)
    private val _refundAmountInvalidState = MutableLiveData(RefundAmountInvalidState.NONE)
    private val _refundEnabled = MutableLiveData(false)

    private var amountToRefund = ""

    //region exposed observable state
    val paymentWithRefunds: LiveData<PaymentWithRefunds?> = _paymentWithRefunds
    val refundedTotal: LiveData<BigDecimal> = _refundedTotal
    val totalNotRefunded: LiveData<BigDecimal?> = _totalNotRefunded
    val refundAmountInvalidState: LiveData<RefundAmountInvalidState> = _refundAmountInvalidState
    val refundEnabled: LiveData<Boolean> = _refundEnabled
    //endregion

    init {
        viewModelScope.launch {
            observePaymentWithRefundsForIdUseCase.paymentWithRefunds(paymentId)?.collect {
                internalPaymentWithRefunds = it
                interalRefundedTotal = it.refundedTotal

                _paymentWithRefunds.postValue(internalPaymentWithRefunds)
                _refundedTotal.postValue(interalRefundedTotal)
                _totalNotRefunded.postValue(it.payment.amount.subtract(interalRefundedTotal))

                checkAmountToRefundValidState()
            }
        }
    }

    //region user events
    fun onAmountToRefundChanged(amountToRefund: String) {
        this.amountToRefund = amountToRefund
        checkAmountToRefundValidState()
    }

    fun onRefundSubmitClicked() = viewModelScope.launch {
        submitNewRefundUseCase.newRefund(paymentId, amountToRefund)
    }
    //endregion

    private fun checkAmountToRefundValidState() {
        val isZeroOrEmpty = (amountToRefund.isEmpty()
                || (inputValidator.isAmountValid(amountToRefund)
                && UiLogicFormatMapper.getAmountFromUI(amountToRefund).compareTo(BigDecimal.ZERO) == 0))

        if (isZeroOrEmpty) {
            _refundEnabled.value = false
            _refundAmountInvalidState.value = RefundAmountInvalidState.NONE
            return
        }

        val amountToRefundInvalidState = getAmountToRefundInvalidState(amountToRefund)
        _refundAmountInvalidState.value = amountToRefundInvalidState
        _refundEnabled.value = amountToRefundInvalidState == RefundAmountInvalidState.NONE
    }

    private fun getAmountToRefundInvalidState(amountToRefund: String): RefundAmountInvalidState {
        val isValid: Boolean = inputValidator.isAmountValid(amountToRefund)
        if (!isValid) {
            return RefundAmountInvalidState.INVALID
        }

        val amount: BigDecimal = UiLogicFormatMapper.getAmountFromUI(amountToRefund)
        val newTotalRefunded = interalRefundedTotal.add(amount)
        val paymentTotal: BigDecimal = internalPaymentWithRefunds?.payment?.amount ?: return RefundAmountInvalidState.NONE

        return if (newTotalRefunded > paymentTotal) {
            RefundAmountInvalidState.INVALID_MAX_REACHED
        } else RefundAmountInvalidState.NONE
    }

    enum class RefundAmountInvalidState {
        NONE, INVALID, INVALID_MAX_REACHED
    }
}