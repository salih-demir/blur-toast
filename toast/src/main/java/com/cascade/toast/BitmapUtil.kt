package com.cascade.toast

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.view.View

object BitmapUtil {
    fun cropBitmap(bitmap: Bitmap, x: Int, y: Int, width: Int, height: Int): Bitmap {
        val croppedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(croppedBitmap)
        val desRect = Rect(0, 0, width, height)
        val srcRect = Rect(x, y, x + width, y + height)

        canvas.drawBitmap(bitmap, srcRect, desRect, null)
        return croppedBitmap
    }

    fun blurBitmap(context: Context?, bitmap: Bitmap, bitmapScale: Float, blurRadius: Float): Bitmap? {
        if (context == null)
            return null

        val width = Math.round(bitmap.width * bitmapScale)
        val height = Math.round(bitmap.height * bitmapScale)

        val inputBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false)
        val outputBitmap = Bitmap.createBitmap(inputBitmap)

        val rs = RenderScript.create(context)
        val theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        val tmpIn = Allocation.createFromBitmap(rs, inputBitmap)
        val tmpOut = Allocation.createFromBitmap(rs, outputBitmap)
        theIntrinsic.setRadius(blurRadius)
        theIntrinsic.setInput(tmpIn)
        theIntrinsic.forEach(tmpOut)
        tmpOut.copyTo(outputBitmap)

        return outputBitmap
    }

    fun getViewVisual(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        view.layout(view.left, view.top, view.right, view.bottom)
        view.draw(canvas)

        return bitmap
    }
}