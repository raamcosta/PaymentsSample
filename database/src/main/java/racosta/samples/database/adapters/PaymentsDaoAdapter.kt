package racosta.samples.database.adapters

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import racosta.samples.core.repository.daoports.PaymentsDaoPort
import racosta.samples.core.model.Payment
import racosta.samples.core.model.PaymentWithRefunds
import racosta.samples.database.adapters.mappers.toCore
import racosta.samples.database.adapters.mappers.toEntity
import racosta.samples.database.daos.PaymentsDao

class PaymentsDaoAdapter(private val paymentsDao: PaymentsDao): PaymentsDaoPort {

    override suspend fun insertAll(vararg payments: Payment) {
        paymentsDao.insertAll(*payments.map { it.toEntity() }.toTypedArray())
    }

    override fun observePaymentsWithRefunds(): Flow<List<PaymentWithRefunds>> {
        return paymentsDao.observePaymentsWithRefunds().map { list ->
            list.map { it.toCore() }
        }
    }

    override fun observePaymentWithRefunds(id: Int): Flow<PaymentWithRefunds>? {
        return paymentsDao.observePaymentWithRefunds(id)?.map { it.toCore() }
    }

    override suspend fun getPaymentWithRefunds(id: Int): PaymentWithRefunds? {
        return paymentsDao.getPaymentWithRefunds(id)?.toCore()
    }
}