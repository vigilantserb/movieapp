package com.stameni.com.movieapp.util

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation

class ResizeAnimation(
    internal var view: View,
    private val targetHeight: Int,
    private var startHeight: Int,
    private var descent: Boolean
) :
    Animation() {

    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        changeHeight(interpolatedTime, descent)
    }

    private fun changeHeight(interpolatedTime: Float, descent: Boolean) {
        val newHeight = if (!descent) {
            (startHeight + targetHeight * interpolatedTime).toInt()
        } else {
            (startHeight + (targetHeight - startHeight) * interpolatedTime).toInt()
        }
        view.layoutParams.height = newHeight
        view.requestLayout()
    }

    override fun willChangeBounds(): Boolean {
        return true
    }
}