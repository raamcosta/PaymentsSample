package racosta.samples.core.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import org.koin.core.component.KoinComponent
import org.koin.core.definition.BeanDefinition
import org.koin.core.module.Module
import org.koin.core.parameter.DefinitionParameters
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import org.koin.dsl.module
import racosta.samples.commons.Logger
import racosta.samples.core.commons.CurrentDateProvider
import racosta.samples.core.commons.PaymentUserInputToModelMapper
import racosta.samples.core.commons.PaymentUserInputValidator
import racosta.samples.core.logic.ObserveAllPaymentsUseCase
import racosta.samples.core.logic.ObservePaymentWithRefundsForIdUseCase
import racosta.samples.core.logic.SubmitNewPaymentUseCase
import racosta.samples.core.logic.SubmitNewRefundUseCase
import racosta.samples.core.repository.Repository
import racosta.samples.core.repository.daoports.DatabaseFacade
import racosta.samples.core.repository.network.PaymentsService
import retrofit2.Retrofit

internal fun coreModule(
    databaseFacade: DatabaseFacade,
    loggerFactory: LoggerFactory,
) = module {

    //utils
    single { CurrentDateProvider() }
    single { loggerFactory }
    factoryWithLogger { PaymentUserInputValidator(get(), logger()) }
    factory { PaymentUserInputToModelMapper(get(), get()) }

    //network
    single {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
            .build()
    }
    single { get<Retrofit>().create(PaymentsService::class.java) }

    //repo
    singleWithLogger(createdAtStart = true) { Repository(databaseFacade, get(), logger()) }

    //core logic
    factory { SubmitNewPaymentUseCase(get(), get()) }
    factory { ObserveAllPaymentsUseCase(get()) }
    factory { ObservePaymentWithRefundsForIdUseCase(get()) }
    factory { SubmitNewRefundUseCase(get(), get()) }
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

private inline fun <reified T> Module.singleWithLogger(
    qualifier: Qualifier? = null,
    createdAtStart: Boolean = false,
    override: Boolean = false,
    noinline definition: Scoped.(DefinitionParameters) -> T
): BeanDefinition<T> {
    return single(qualifier, createdAtStart, override) {
        Scoped(this, T::class.java).definition(it)
    }
}

private class Scoped(val scope: Scope, val clazz: Class<*>) : KoinComponent

private inline fun <reified T : Any> Scoped.get() = scope.get<T>()
private fun Scoped.logger() = get<LoggerFactory>().invoke(clazz)

private typealias LoggerFactory = (Class<*>) -> Logger