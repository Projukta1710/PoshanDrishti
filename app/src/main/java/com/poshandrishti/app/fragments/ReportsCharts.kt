package com.poshandrishti.app.fragments

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class NutritionBarChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var normal = 36f
    private var mam = 9f
    private var sam = 3f

    private val months = listOf("Jun", "Jul", "Aug", "Sep", "Oct", "Nov")

    fun setValues(values: List<Float>) {
        if (values.size >= 3) {
            normal = values[0]
            mam = values[1]
            sam = values[2]
        }
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val left = 12f
        val right = width - 12f
        val top = 12f
        val bottom = height - 24f

        val barCount = 6
        val slotWidth = (right - left) / barCount
        val barWidth = slotWidth * 0.62f

        val maxTotal = (normal + mam + sam).coerceAtLeast(1f)

        val normalColor = Color.rgb(77, 151, 84)
        val mamColor = Color.rgb(231, 173, 59)
        val samColor = Color.rgb(202, 72, 65)

        // Six monthly stacked bars
        for (i in 0 until barCount) {
            val variation = when (i) {
                0 -> 0.94f
                1 -> 0.98f
                2 -> 0.96f
                3 -> 1.00f
                4 -> 1.02f
                else -> 1.00f
            }

            val n = normal * variation
            val m = mam * variation
            val s = sam * variation
            val total = n + m + s

            val x = left + slotWidth * i + (slotWidth - barWidth) / 2f
            val totalHeight = bottom - top

            val normalHeight = totalHeight * n / maxTotal
            val mamHeight = totalHeight * m / maxTotal
            val samHeight = totalHeight * s / maxTotal

            paint.color = normalColor
            canvas.drawRect(
                x,
                bottom - normalHeight,
                x + barWidth,
                bottom,
                paint
            )

            paint.color = mamColor
            canvas.drawRect(
                x,
                bottom - normalHeight - mamHeight,
                x + barWidth,
                bottom - normalHeight,
                paint
            )

            paint.color = samColor
            canvas.drawRect(
                x,
                bottom - normalHeight - mamHeight - samHeight,
                x + barWidth,
                bottom - normalHeight - mamHeight,
                paint
            )

            paint.color = Color.DKGRAY
            paint.textSize = 11f * resources.displayMetrics.scaledDensity
            paint.textAlign = Paint.Align.CENTER

            canvas.drawText(
                months[i],
                x + barWidth / 2f,
                height - 5f,
                paint
            )
        }
    }
}


class GrowthLineChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var values = listOf(1.2f, 0.8f, 0.4f, 0.0f, -0.4f, -0.8f)

    fun setValues(newValues: List<Float>) {
        if (newValues.isNotEmpty()) {
            values = newValues
        }
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val left = 10f
        val right = width - 28f
        val top = 8f
        val bottom = height - 8f

        val chartHeight = bottom - top

        // Background reference zones
        paint.style = Paint.Style.FILL

        paint.color = Color.rgb(226, 244, 226)
        canvas.drawRect(left, top, right, top + chartHeight * 0.58f, paint)

        paint.color = Color.rgb(255, 241, 203)
        canvas.drawRect(
            left,
            top + chartHeight * 0.58f,
            right,
            top + chartHeight * 0.80f,
            paint
        )

        paint.color = Color.rgb(250, 224, 222)
        canvas.drawRect(
            left,
            top + chartHeight * 0.80f,
            right,
            bottom,
            paint
        )

        // Reference labels
        paint.color = Color.DKGRAY
        paint.textSize = 10f * resources.displayMetrics.scaledDensity
        paint.textAlign = Paint.Align.RIGHT

        canvas.drawText("-2 SD", width - 3f, top + chartHeight * 0.55f, paint)
        canvas.drawText("-3 SD", width - 3f, top + chartHeight * 0.77f, paint)

        // Plot the growth values
        val minValue = -1.2f
        val maxValue = 1.8f

        val path = Path()
        val points = mutableListOf<Pair<Float, Float>>()

        values.forEachIndexed { index, value ->

            val x = if (values.size == 1) {
                (left + right) / 2f
            } else {
                left + index * (right - left) / (values.size - 1)
            }

            val normalized = ((maxValue - value) / (maxValue - minValue))
                .coerceIn(0f, 1f)

            val y = top + normalized * chartHeight

            points.add(Pair(x, y))

            if (index == 0) path.moveTo(x, y)
            else path.lineTo(x, y)
        }

        // Black connecting line
        paint.color = Color.rgb(30, 34, 29)
        paint.strokeWidth = 2.5f
        paint.style = Paint.Style.STROKE
        canvas.drawPath(path, paint)

        // Visit points
        paint.style = Paint.Style.FILL

        points.forEachIndexed { index, point ->
            paint.color = if (index == points.lastIndex) {
                Color.rgb(180, 48, 38)
            } else {
                Color.rgb(30, 34, 29)
            }

            canvas.drawCircle(point.first, point.second, 4f, paint)
        }
    }
}
