package com.android.pizzaapp.models

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.android.pizzaapp.R
import com.android.pizzaapp.utils.InfoBSDialog
import com.android.pizzaapp.utils.isOnline
import java.io.InterruptedIOException
import java.net.UnknownHostException

data class ApiResult<T>(val data: T? = null, val error: ApiError? = null)

data class ApiError(val code: Int = -1, val message: String? = "Unknown Error", val exception: Throwable? = null)

enum class HttpError(val value: Int) {
    BAD_REQUEST(400), UNAUTHORIZED(401), NOT_FOUND(404), INTERNAL_SERVER_ERROR(500), UNKNOWN(-1);

    companion object {
        fun valueOf(value: Int): HttpError = values().find { it.value == value } ?: UNKNOWN
    }
}

fun ApiError.handlerError(manager: FragmentManager, context: Context, finishActivity : Boolean = false) {
    InfoBSDialog(getErrorTitle(context), getErrorMessage(context), finishActivity)
        .show(manager, "CommonErrorDialog")
}

fun ApiError.getErrorTitle(context: Context): String {
    return context.getString(
        when {
            exception is InterruptedIOException && message?.equals("timeout", true) == true -> R.string.api_error_timeout
            exception is UnknownHostException || !isOnline(context) -> R.string.no_internet_error_title
            else ->
                when (HttpError.valueOf(code)) {
                    HttpError.BAD_REQUEST, HttpError.INTERNAL_SERVER_ERROR -> R.string.api_error_bad_request
                    HttpError.UNAUTHORIZED -> R.string.api_error_unauthorised
                    HttpError.NOT_FOUND -> R.string.api_error_not_found
                    HttpError.UNKNOWN -> R.string.general_error_title
                }
        }
    ) + if (code != -1) " - $code" else ""
}

fun ApiError.getErrorMessage(context: Context): String {
    return context.getString(
        when {
            exception is InterruptedIOException && message?.equals("timeout", true) == true -> R.string.api_error_timeout_desc
            exception is UnknownHostException || !isOnline(context) -> R.string.no_internet_error_desc
            else ->
                when (HttpError.valueOf(code)) {
                    HttpError.BAD_REQUEST, HttpError.INTERNAL_SERVER_ERROR -> R.string.api_error_bad_request_desc
                    HttpError.UNAUTHORIZED -> R.string.api_error_unauthorised_desc
                    HttpError.NOT_FOUND -> R.string.api_error_not_found_desc
                    HttpError.UNKNOWN -> if (!message.isNullOrBlank()) return message
                    else R.string.api_error_title_unknown_desc
                }
        }
    )
}