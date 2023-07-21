package com.osamaaftab.teya.common

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

object AssetReaderManager {

    fun asset(context: Context, assetPath: String): String {
        try {
            val inputStream = context.assets.open(assetPath)
            return inputStreamToString(inputStream)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    private fun inputStreamToString(inputStream: InputStream): String {
        val builder = StringBuilder()
        val reader = InputStreamReader(inputStream, "UTF-8")
        reader.readLines().forEach {
            builder.append(it)
        }
        return builder.toString()
    }
}