package com.osamaaftab.teya.common

import android.content.Context
import android.net.Uri
import androidx.test.platform.app.InstrumentationRegistry
import com.osamaaftab.teya.common.AssetReaderManager.asset
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class SuccessDispatcher(
    private val context: Context = (InstrumentationRegistry.getInstrumentation().targetContext
        .applicationContext)
) : Dispatcher() {

    private val responseFilesByPath: Map<String, String> = mapOf(
        APIPaths.ALBUM_LIST to MockFiles.ALBUM_LIST_SUCCESS
    )

    override fun dispatch(request: RecordedRequest): MockResponse {
        val errorResponse = MockResponse().setResponseCode(404)

        val pathWithoutQueryParams = Uri.parse(request.path).path ?: return errorResponse
        val responseFile = responseFilesByPath[pathWithoutQueryParams]
        return if (responseFile != null) {
            val responseBody = asset(context, responseFile)
            MockResponse().setResponseCode(200).setBody(responseBody)
        } else {
            errorResponse
        }
    }
}