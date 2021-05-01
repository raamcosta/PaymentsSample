package racosta.samples.core.logic

import racosta.samples.core.daoports.PaymentsDaoPort

class ObservePaymentWithRefundsForIdUseCase(private val paymentsDao: PaymentsDaoPort) {

    fun paymentWithRefunds(id: Int) = paymentsDao.observePaymentWithRefunds(id)
}