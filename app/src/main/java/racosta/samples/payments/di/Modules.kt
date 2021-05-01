package racosta.samples.payments.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import racosta.samples.core.Core
import racosta.samples.database.initDb
import racosta.samples.payments.commons.AndroidLogger
import racosta.samples.payments.ui.viewmodels.NewPaymentViewModel
import racosta.samples.payments.ui.viewmodels.PaymentsHistoryViewModel

val appModule = module {

    //init other core modules
    single(createdAtStart = true) { initDb(get()) }
    single(createdAtStart = true) { Core(get()) { AndroidLogger(it) } }

    //core components
    factory { get<Core>().submitNewPaymentUseCase }
    factory { get<Core>().paymentInputValidator }

    //view models
    viewModel { PaymentsHistoryViewModel() }
    viewModel { NewPaymentViewModel(get(), get()) }
}