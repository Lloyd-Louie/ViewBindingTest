package com.example.multilanguage.utils


import com.example.multilanguage.api.ApiService
import com.example.multilanguage.repositories.UserRepository
import com.example.multilanguage.repositories.UserRepositoryImpl
import com.example.multilanguage.viewmodel.MemeViewModel
import org.koin.dsl.module
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory


val appModules = module{
    /**
     * API Interface
     */
    single {
        createApiService<ApiService>(
            okHttpClient = createHttpClient(),
            factory = RxJava2CallAdapterFactory.create(),
            baseUrl = "https://mocki.io/"
        )
    }
    /**
     * Repositories
     */
    factory<UserRepository> { UserRepositoryImpl(apiService = get()) }

    single { MemeViewModel(userRepository = get()) }
}



