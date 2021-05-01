package racosta.samples.core.logic

import racosta.samples.core.daoports.PaymentsDaoPort

class ObserveAllPaymentsUseCase internal constructor(private val paymentsDao: PaymentsDaoPort) {

    val payments get() = paymentsDao.observePaymentsWithRefunds()
}