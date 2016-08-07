package com.github.ming13.oktwitter.graphics

import android.content.Context
import android.graphics.*
import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat
import com.github.ming13.oktwitter.R
import com.squareup.picasso.Transformation

class PicassoCircleTransformation(context: Context) : Transformation
{
    @ColorInt
    private val strokeColor: Int
    private val strokeSize: Int

    init {
        this.strokeColor = ContextCompat.getColor(context, R.color.background_divider)
        this.strokeSize = context.resources.getDimensionPixelSize(R.dimen.size_divider)
    }

    override fun transform(sourceBitmap: Bitmap): Bitmap {
        val imageSize = Math.min(sourceBitmap.width, sourceBitmap.height)
        val imageRadius = imageSize / 2f

        val circlePaint = createCirclePaint(sourceBitmap)
        val strokePaint = createStrokePaint()

        val destinationBitmap = Bitmap.createBitmap(imageSize, imageSize, Bitmap.Config.ARGB_8888)

        val imageCanvas = Canvas(destinationBitmap)

        imageCanvas.drawCircle(imageRadius, imageRadius, imageRadius - strokeSize, circlePaint)
        imageCanvas.drawCircle(imageRadius, imageRadius, imageRadius - strokeSize, strokePaint)

        sourceBitmap.recycle()

        return destinationBitmap
    }

    private fun createCirclePaint(bitmap: Bitmap): Paint {
        val paint = Paint()

        paint.isAntiAlias = true
        paint.shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        return paint
    }

    private fun createStrokePaint(): Paint {
        val paint = Paint()

        paint.isAntiAlias = true
        paint.color = strokeColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeSize.toFloat()

        return paint
    }

    override fun key(): String {
        return this.javaClass.toString()
    }
}