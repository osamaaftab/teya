package com.osamaaftab.teya

import android.app.Application
import android.os.Bundle
import android.os.StrictMode
import androidx.test.runner.AndroidJUnitRunner
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.core.context.loadKoinModules
import retrofit2.Retrofit
import org.koin.dsl.module

import retrofit2.converter.moshi.MoshiConverterFactory


class MockTestRunner : AndroidJUnitRunner() {
    override fun onCreate(arguments: Bundle?) {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
        super.onCreate(arguments)
    }

    override fun callApplicationOnCreate(app: Application?) {
        super.callApplicationOnCreate(app)
        loadKoinModules(module {
            single { providesTestRetrofit(get(), get()) }
        })
    }

    private fun providesTestRetrofit(
        okHttpClient: OkHttpClient, moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://localhost:$MOCK_WEB_SERVER_PORT")
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(CoroutineCallAdapterFactory()).build()
    }


    companion object {
        const val MOCK_WEB_SERVER_PORT = 4007
    }
}