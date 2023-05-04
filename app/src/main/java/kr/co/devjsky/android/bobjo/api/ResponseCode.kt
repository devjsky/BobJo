package kr.co.devjsky.android.bobjo.api

interface ResponseCode {
    companion object {
        const val OK: Int = 200
        const val ACCEPTED: Int = 202
        const val NO_CONTENT: Int = 204

        const val BAD_REQUEST: Int = 400
        const val FORBIDDEN: Int = 403
        const val NOT_FOUND: Int = 404
        const val CONFLICT: Int = 409

        const val SERVER_ERROR: Int = 500
        const val API_ERROR: Int = -1
    }
}