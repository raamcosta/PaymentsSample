package racosta.samples.core.di

import org.koin.core.component.KoinComponent
import org.koin.core.definition.BeanDefinition
import org.koin.core.parameter.DefinitionParameters
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import org.koin.dsl.module
import org.koin.core.module.Module
import racosta.samples.commons.Logger
import racosta.samples.core.logic.SubmitNewPaymentUseCase
import racosta.samples.core.commons.CurrentDateProvider
import racosta.samples.core.commons.PaymentUserInputToModelMapper
import racosta.samples.core.commons.PaymentUserInputValidator
import racosta.samples.core.daoports.DatabaseApi
import racosta.samples.core.logic.ObserveAllPaymentsUseCase

internal fun coreModule(
    databaseApi: DatabaseApi,
    loggerFactory: LoggerFactory,
) = module {

    //utils
    single { CurrentDateProvider() }
    single { loggerFactory }
    factoryWithLogger { PaymentUserInputValidator(get(), logger()) }
    factory { PaymentUserInputToModelMapper(get(), get()) }

    //db
    factory { databaseApi.payments() }

    //core logic
    factory { SubmitNewPaymentUseCase(get(), get()) }
    factory { ObserveAllPaymentsUseCase(get()) }
}

private inline fun <reified T> Module.factoryWithLogger(
    qualifier: Qualifier? = null,
    override: Boolean = false,
    noinline definition: Scoped.(DefinitionParameters) -> T
): BeanDefinition<T> {
    return factory(qualifier, override) {
        Scoped(this, T::class.java).definition(it)
    }
}

private class Scoped(val scope: Scope, val clazz: Class<*>): KoinComponent

private inline fun <reified T : Any> Scoped.get() = scope.get<T>()
private fun Scoped.logger() = get<LoggerFactory>().invoke(clazz)

private typealias LoggerFactory = (Class<*>) -> Logger