package com.osamaaftab.teya.domain.model

data class ErrorModel(
    val message: String?,
    val code: Int?,
    var errorStatus: ErrorStatus
) {
    constructor(message: String?, errorStatus: ErrorStatus) : this(message, null, errorStatus)

    /**
     * various error status to know what happened if something goes wrong with a repository call
     */
    enum class ErrorStatus {
        /**
         * error in connecting to repository (Server or Database)
         */
        NO_CONNECTION,
        /**
         * error in getting value (Json Error, Server Error, etc)
         */
        BAD_RESPONSE,
        /**
         * Time out  error
         */
        TIMEOUT,
        /**
         * an unexpected error
         */
        NOT_DEFINED,
        /**
         * bad credential
         */
        UNAUTHORIZED
    }
}