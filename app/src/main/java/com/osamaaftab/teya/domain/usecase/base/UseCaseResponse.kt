package com.osamaaftab.teya.domain.usecase.base

import com.osamaaftab.teya.domain.model.ErrorModel

interface UseCaseResponse<in Type> {
    fun onSuccess(result: Type)
    fun onError(errorModel: ErrorModel?)
}