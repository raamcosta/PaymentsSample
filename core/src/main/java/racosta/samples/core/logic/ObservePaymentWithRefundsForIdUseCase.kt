package racosta.samples.core.logic

import racosta.samples.core.repository.Repository

class ObservePaymentWithRefundsForIdUseCase internal constructor(private val repository: Repository) {

    fun paymentWithRefunds(id: Int) = repository.observePaymentWithRefunds(id)
}