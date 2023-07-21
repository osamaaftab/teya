package com.osamaaftab.teya.di.module

import com.osamaaftab.teya.data.ApiErrorHandle
import org.koin.dsl.module

val AppModule = module {
    single { provideApiError() }
}

fun provideApiError(): ApiErrorHandle {
    return ApiErrorHandle()
}