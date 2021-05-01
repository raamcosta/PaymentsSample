package racosta.samples.payments.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import racosta.samples.core.logic.ObserveAllPaymentsUseCase
import racosta.samples.core.model.PaymentWithRefunds
import racosta.samples.payments.commons.toNavAction
import racosta.samples.payments.ui.screens.base.BaseViewModel
import racosta.samples.payments.ui.screens.paymentshistory.PaymentsHistoryFragmentDirections

class PaymentsHistoryViewModel(
    private val observeAllPaymentsUseCase: ObserveAllPaymentsUseCase
) : BaseViewModel() {

    private var unfilteredPayments = emptyList<PaymentWithRefunds>()
    private var isFilteringForRefunds = false
    private val _isFiltering = MutableLiveData(isFilteringForRefunds)
    private val _payments = MutableLiveData(unfilteredPayments)

    //region exposed state
    val payments: LiveData<List<PaymentWithRefunds>> = _payments
    val isFiltering: LiveData<Boolean> = _isFiltering
    //endregion

    init {
        viewModelScope.launch {
            observeAllPaymentsUseCase.payments.collect {
                unfilteredPayments = it
                _payments.value = filterListIfNeeded()
            }
        }
    }

    //region user events
    fun onPaymentClick(payment: PaymentWithRefunds) {
        navigate(PaymentsHistoryFragmentDirections.actionToPaymentDetailsFragment(payment.payment.id).toNavAction())
    }

    fun onNewPaymentClick() {
        navigate(PaymentsHistoryFragmentDirections.actionToNewPaymentFragment().toNavAction())
    }

    fun onFilterClick() {
        isFilteringForRefunds = !isFilteringForRefunds
        _isFiltering.value = isFilteringForRefunds

        _payments.value = filterListIfNeeded()
    }

    //endregion

    //region internal logic

    private fun filterListIfNeeded() = if (isFilteringForRefunds) {
        unfilteredPayments.filterListWithRefunds()
    } else {
        unfilteredPayments
    }

    private fun List<PaymentWithRefunds>.filterListWithRefunds() = filter { it.refunds.isNotEmpty() }

    //endregion
}