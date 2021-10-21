package com.example.multilanguage.model

import android.view.View
import androidx.annotation.Keep
import androidx.appcompat.app.AlertDialog
import com.facebook.stetho.server.http.HttpStatus

/**
 * Error Handling
 */
@Keep
data class ServerError(
    val httpStatus: HttpStatus,
    val title: String,
    val details: String,
)

/**
 * Custom alert dialog
 */
@Keep
data class CustomAlertDialog(
    val view: View,
    val alertDialog: AlertDialog
)