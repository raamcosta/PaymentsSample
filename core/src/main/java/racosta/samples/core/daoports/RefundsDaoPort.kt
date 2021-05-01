package racosta.samples.core.daoports

import racosta.samples.core.model.Refund

interface RefundsDaoPort {

    suspend fun insertAll(vararg refunds: Refund)
}