package racosta.samples.payments.ui.viewmodels

import androidx.annotation.RestrictTo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import racosta.samples.payments.commons.NavigationAction
import racosta.samples.payments.ui.screens.PaymentsHistoryFragmentDirections
import racosta.samples.payments.ui.screens.base.BaseViewModel
import java.util.ArrayList

class PaymentsHistoryViewModel(
//    private val observeAllPaymentsUseCase: ObserveAllPaymentsUseCase
) : BaseViewModel() {
//    , ObserveAllPaymentsUseCase.Listener {

//    private var unfilteredPayments = emptyList<PaymentWithRefunds>()
    private var isFilteringForRefunds = false
    private val isFiltering = MutableLiveData(isFilteringForRefunds)
//    private val payments = MutableLiveData<List<PaymentWithRefunds>>(unfilteredPayments)

    init {
//        observeAllPaymentsUseCase.start(this)
    }

    //region exposed UI state
    fun isFiltering(): LiveData<Boolean> {
        return isFiltering
    }

//    fun getPayments(): LiveData<List<PaymentWithRefunds>> {
//        return payments
//    }

    //endregion
    //region user events
//    fun onPaymentClick(payment: PaymentWithRefunds) {
//        val action: PaymentsHistoryFragmentDirections.ActionToPaymentDetailsFragment =
//            PaymentsHistoryFragmentDirections.actionToPaymentDetailsFragment(
//                payment.getPayment().getId()
//            )
//
//        navigate(NavigationAction(action))
//    }

    fun onNewPaymentClick() {
        navigate(NavigationAction(PaymentsHistoryFragmentDirections.actionToNewPaymentFragment()))
    }

    fun onFilterClick() {
        isFilteringForRefunds = !isFilteringForRefunds
        isFiltering.value = isFilteringForRefunds
        postFilteredListToUI()
    }

    //endregion

    //region internal logic

//    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
//    fun onPaymentsChangedEvent(paymentsWithRefunds: List<PaymentWithRefunds>) {
//        unfilteredPayments = paymentsWithRefunds
//        postFilteredListToUI()
//    }

    @RestrictTo(RestrictTo.Scope.SUBCLASSES)
    override fun onCleared() {
//        observeAllPaymentsUseCase.stop()
    }

    private fun postFilteredListToUI() {
//        if (isFilteringForRefunds) {
//            val paymentsForUI: ArrayList<PaymentWithRefunds> = ArrayList<PaymentWithRefunds>()
//            for (paymentWithRefunds in unfilteredPayments) {
//                if (isFilteringForRefunds == !paymentWithRefunds.getRefunds().isEmpty()) {
//                    paymentsForUI.add(paymentWithRefunds)
//                }
//            }
//            payments.postValue(paymentsForUI)
//        } else {
//            payments.postValue(unfilteredPayments)
//        }
    }

    //endregion

    //region VM Factory

//    class Factory(observeAllPaymentsUseCase: ObserveAllPaymentsUseCase) :
//        ViewModelProvider.Factory {
//        private val observeAllPaymentsUseCase: ObserveAllPaymentsUseCase
//        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//            return PaymentsHistoryViewModel(observeAllPaymentsUseCase) as T
//        }
//
//        init {
//            this.observeAllPaymentsUseCase = observeAllPaymentsUseCase
//        }
//    }
    //endregion
}