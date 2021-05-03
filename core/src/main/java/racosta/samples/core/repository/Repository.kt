package racosta.samples.core.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import racosta.samples.commons.Logger
import racosta.samples.core.model.Payment
import racosta.samples.core.model.PaymentWithRefunds
import racosta.samples.core.model.Refund
import racosta.samples.core.repository.daoports.DatabaseFacade
import racosta.samples.core.repository.network.PaymentsService
import racosta.samples.core.repository.network.mappers.toCore
import racosta.samples.core.repository.network.mappers.toDto

internal class Repository constructor(
    private val databaseFacade: DatabaseFacade,
    private val paymentsService: PaymentsService,
    private val logger: Logger
) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val paymentsDaoPort get() = databaseFacade.payments()

    private val refundsDaoPort get() = databaseFacade.refunds()

    init {
        logger.debug("Initializing.. loading network payments and refunds")

        coroutineScope.launch {
            val response = paymentsService.getPayments()
            if (response.isSuccessful && response.body() != null) {
                logger.debug("Inserting ${response.body()} in local db")
                paymentsDaoPort.insertAll(*response.body()!!.map { it.toCore() }.toTypedArray())
            }

            val refundsResponse = paymentsService.getRefunds()
            if (refundsResponse.isSuccessful && refundsResponse.body() != null) {
                logger.debug("Inserting ${refundsResponse.body()} in local db")
                refundsDaoPort.insertAll(*refundsResponse.body()!!.map { it.toCore() }.toTypedArray())
            }
        }
    }

    suspend fun addRefunds(vararg refunds: Refund) = withContext(Dispatchers.IO) {
        logger.debug("Posting $refunds to server")
        refunds.forEach {
            paymentsService.postRefund(it.toDto())
        }

        refundsDaoPort.insertAll(*refunds)
    }

    suspend fun addPayments(vararg payments: Payment) = withContext(Dispatchers.IO) {
        logger.debug("Posting $payments to server")
        payments.forEach {
            paymentsService.postPayment(it.toDto())
        }

        paymentsDaoPort.insertAll(*payments)
    }

    fun observePaymentsWithRefunds() = paymentsDaoPort.observePaymentsWithRefunds()

    fun observePaymentWithRefunds(paymentId: Int): Flow<PaymentWithRefunds>? {
        return paymentsDaoPort.observePaymentWithRefunds(paymentId)
    }

    suspend fun getPaymentWithRefunds(paymentId: Int): PaymentWithRefunds? = withContext(Dispatchers.IO) {
        return@withContext paymentsDaoPort.getPaymentWithRefunds(paymentId)
    }
}