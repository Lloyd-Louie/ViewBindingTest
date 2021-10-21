package com.example.multilanguage.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.multilanguage.R
import com.example.multilanguage.model.CustomAlertDialog

abstract class BaseActivityUtils: AppCompatActivity() {
    // Navigate to next desired activity
    inline fun <reified T : Activity> navigate(
        finishCurrent: Boolean = true,
        requestCode: Int = -1,
        configIntent: Intent.() -> Unit = {}
    ) {
        val intent = newIntent<T>(this)
        intent.configIntent()

        if (requestCode == -1) {
            startActivity(intent)
        } else {
            startActivityForResult(intent, requestCode, Bundle())
        }

        if (finishCurrent) {
            (this as Activity).finish()
        }
//
//        when (navigationDirection) {
//            NavigationDirection.FORWARD -> {
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
//            }
//            NavigationDirection.BACKWARD -> {
//                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
//            }
//            NavigationDirection.UPWARD -> {
//                overridePendingTransition(R.anim.slide_up, R.anim.slide_down)
//            }
//        }
    }
    /**
     * Create custom alert dialog
     * layout.view.* to call the UI elements
     */
    fun createCustomAlertDialog(cancellable: Boolean, layout: Int): CustomAlertDialog {
        val dialogView = LayoutInflater.from(this).inflate(layout, null)

        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialogTheme).apply {
            setView(dialogView)
            setCancelable(cancellable)
        }

        val dialog = builder.show()

        return CustomAlertDialog(dialogView, dialog)
    }

    // Generate intent
    inline fun <reified T : Any> newIntent(context: Context): Intent = Intent(context, T::class.java)
}