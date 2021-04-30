package racosta.samples.payments.di

import org.koin.dsl.module

val appModule = module {

    // single instance of HelloRepository
//    single<HelloRepository> { HelloRepositoryImpl() }

    // MyViewModel ViewModel
//    viewModel { MyViewModel(get()) }
}