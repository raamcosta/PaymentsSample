package racosta.samples.core.logic

import racosta.samples.core.repository.Repository

class ObserveAllPaymentsUseCase internal constructor(private val repository: Repository) {

    val payments get() = repository.observePaymentsWithRefunds()
}