package com.cascade.toast

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.drawable.*
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat

/**
 * Created by Salih Demir
 * GitHub (https://github.com/salih-demir)
 */

object BlurToast {
    private const val BITMAP_SCALE = 0.15f
    private const val BLUR_RADIUS = 2f
    private const val CORNER_RADIUS = 200f

    @SuppressLint("InflateParams")
    fun buildToast(activity: Activity, message: String, blur: Float): Toast {
        //Toast has not a parent view, pass root as null and suppress the lint.
        val viewToast = LayoutInflater.from(activity).inflate(R.layout.content_toast, null)
        val viewWindow = activity.window.decorView
        val bitmapWindow = BitmapUtil.getViewVisual(viewWindow)

        val backgroundColor = ContextCompat.getColor(activity, R.color.colorToastBackground)
        val gradientDrawable = GradientDrawable()
        gradientDrawable.setColor(backgroundColor)
        gradientDrawable.cornerRadius = CORNER_RADIUS

        viewToast.background = gradientDrawable
        viewToast.viewTreeObserver.addOnGlobalLayoutListener {
            val location = IntArray(2)
            viewToast.getLocationOnScreen(location)

            val x = location[0]
            val y = location[1]
            val width = viewToast.width
            val height = viewToast.height

            val croppedBitmap = BitmapUtil.cropBitmap(bitmapWindow, x, y, width, height)
            val blurredBitmap = BitmapUtil.blurBitmap(activity, croppedBitmap, BITMAP_SCALE, blur)

            val roundedDrawable = RoundedBitmapDrawableFactory.create(activity.resources, blurredBitmap)
            roundedDrawable.cornerRadius = CORNER_RADIUS

            val drawableLayers = arrayOf(roundedDrawable, gradientDrawable)
            val layerDrawable = LayerDrawable(drawableLayers)

            viewToast.background = layerDrawable
        }

        val textViewMessage = viewToast.findViewById<TextView>(R.id.tv_message)
        textViewMessage.text = message

        val toast = Toast(activity)
        toast.view = viewToast

        return toast
    }

    fun buildToast(activity: Activity, message: String): Toast {
        return buildToast(activity, message, BLUR_RADIUS)
    }
}