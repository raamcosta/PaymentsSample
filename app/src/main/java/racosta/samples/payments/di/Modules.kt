package racosta.samples.payments.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import racosta.samples.core.Core
import racosta.samples.database.initDb
import racosta.samples.payments.commons.AndroidLogger
import racosta.samples.payments.ui.screens.paymentdetails.PaymentDetailsViewModel
import racosta.samples.payments.ui.viewmodels.NewPaymentViewModel
import racosta.samples.payments.ui.viewmodels.PaymentsHistoryViewModel

val appModule = module {

    //init other core modules
    single(createdAtStart = true) { initDb(get()) }
    single(createdAtStart = true) { Core(get()) { AndroidLogger(it) } }

    //core components
    factory { get<Core>().submitNewPaymentUseCase }
    factory { get<Core>().paymentInputValidator }
    factory { get<Core>().observeAllPaymentsUseCase }
    factory { get<Core>().observePaymentWithRefundsForIdUseCase }
    factory { get<Core>().submitNewRefundUseCase }

    //view models
    viewModel { PaymentsHistoryViewModel(get()) }
    viewModel { NewPaymentViewModel(get(), get()) }
    viewModel { (paymentId: Int) -> PaymentDetailsViewModel(paymentId, get(), get(), get())  }
}