package com.example.multilanguage.utils

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.multilanguage.R

private const val mTag = GLOBAL_TAG + "ExtensionsV2"
/**
 * Throttle
 */
fun View.setSafeOnClickListener(debounceTime: Long = 500L, action: () -> Unit) {
    setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0
        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) {
                return
            } else {
                action()
            }
            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}
/**
 * DialogFragment
 */
fun DialogFragment.safeShow(fragmentManager: FragmentManager, bundle: Bundle.() -> Unit = {}) {
    if (this.isAdded) {
        Log.e(mTag, "Only 1 instance of that dialog fragment is permitted")
        return
    }

    arguments = Bundle().apply(bundle)
    fragmentManager.beginTransaction().add(this, hashCode().toString()).commitAllowingStateLoss()
}

fun DialogFragment.safeDismiss(fragmentManager: FragmentManager) {
    fragmentManager.beginTransaction().remove(this).commitAllowingStateLoss()
}

/**
 * View
 */
fun View.gone() {
    visibility = View.GONE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.isVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

/**
 *ImageView
 */
fun AppCompatImageView.loadUrlRounded(imageUrl: String?) {
    if (imageUrl == null || imageUrl.isEmpty()) return

    Glide.with(context)
        .load(imageUrl)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .transition(DrawableTransitionOptions.withCrossFade())
        .apply(RequestOptions().transforms(RoundedCorners(24)))
        .into(this)
}
/**
 *ImageView
 */
fun AppCompatImageView.loadUrl(imageUrl: String?) {
    if (imageUrl == null || imageUrl.isEmpty()) return
    Glide.with(context)
        .load(imageUrl)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}
