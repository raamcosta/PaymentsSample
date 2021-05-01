package racosta.samples.core

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.dsl.koinApplication
import racosta.samples.commons.Logger
import racosta.samples.core.commons.PaymentUserInputValidator
import racosta.samples.core.daoports.DatabaseApi
import racosta.samples.core.di.coreModule
import racosta.samples.core.logic.SubmitNewPaymentUseCase

class Core(
    databaseApi: DatabaseApi,
    loggerFactory: (Class<*>) -> Logger
): KoinComponent {

    private val koin = koinApplication {
        modules(coreModule(databaseApi, loggerFactory))
    }.koin

    override fun getKoin() = koin

    val submitNewPaymentUseCase get() = get<SubmitNewPaymentUseCase>()
    val paymentInputValidator get() = get<PaymentUserInputValidator>()
}