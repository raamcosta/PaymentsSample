package racosta.samples.core.repository.daoports

interface DatabaseFacade {
    fun payments(): PaymentsDaoPort

    fun refunds(): RefundsDaoPort
}