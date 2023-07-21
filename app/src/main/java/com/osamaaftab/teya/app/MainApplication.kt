package com.osamaaftab.teya.app

import android.app.Application
import com.osamaaftab.teya.di.module.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(
                listOf(
                    ApiServicesModule,
                    AppModule,
                    NetWorkModule,
                    RepositoryModule,
                    UseCaseModule,
                    ActivityModule,
                )
            )
        }
    }
}