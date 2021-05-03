package racosta.samples.core.repository.daoports

import kotlinx.coroutines.flow.Flow
import racosta.samples.core.model.Payment
import racosta.samples.core.model.PaymentWithRefunds

interface PaymentsDaoPort {

    suspend fun insertAll(vararg payments: Payment)

    fun observePaymentsWithRefunds(): Flow<List<PaymentWithRefunds>>

    fun observePaymentWithRefunds(id: Int): Flow<PaymentWithRefunds>?

    suspend fun getPaymentWithRefunds(id: Int): PaymentWithRefunds?
}