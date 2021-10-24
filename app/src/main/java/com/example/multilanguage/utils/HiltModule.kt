package com.example.multilanguage.utils


import com.example.multilanguage.BuildConfig
import com.example.multilanguage.api.ApiService
import com.example.multilanguage.repositories.UserRepository
import com.example.multilanguage.repositories.UserRepositoryImpl
import com.example.multilanguage.viewmodel.MemeViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.koin.dsl.module
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object  HiltModule{
    @Singleton
    @Provides
    fun createApiClient(): ApiService {
        return createApiService(
            okHttpClient = createHttpClient(),
            factory = RxJava2CallAdapterFactory.create(),
            baseUrl = "https://mocki.io/"
        )
    }
    @Singleton
    @Provides
    fun createUserRepository(apiService: ApiService): UserRepository {
        return UserRepositoryImpl(apiService)
    }
}


