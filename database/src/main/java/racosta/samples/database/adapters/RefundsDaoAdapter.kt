package racosta.samples.database.adapters

import racosta.samples.core.repository.daoports.RefundsDaoPort
import racosta.samples.core.model.Refund
import racosta.samples.database.adapters.mappers.toEntity
import racosta.samples.database.daos.RefundsDao

class RefundsDaoAdapter(private val refundsDao: RefundsDao): RefundsDaoPort {

    override suspend fun insertAll(vararg refunds: Refund) {
        refundsDao.insertAll(*refunds.map { it.toEntity() }.toTypedArray())
    }
}