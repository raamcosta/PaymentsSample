package racosta.samples.core.daoports

interface DatabaseApi {
    fun payments(): PaymentsDaoPort

    fun refunds(): RefundsDaoPort
}