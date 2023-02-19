package com.vk.directop.odinfragmentsexample

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.*
import kotlin.math.cos
import kotlin.math.sin
import kotlin.properties.Delegates

class CoolGraphView
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var padding = 0
    private var fontSize = 0
    private val numeralSpacing = 0
    private var handTruncation = 0
    private var hourHandTruncation = 0
    private var radius = 0f
    lateinit var paint: Paint
    private var isInit = false
    private val numbers = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)

    private var hourColor by Delegates.notNull<Int>()
    private var minuteColor by Delegates.notNull<Int>()
    private var secondColor by Delegates.notNull<Int>()
    private var hourHandLength by Delegates.notNull<Float>()
    private var minuteHandLength by Delegates.notNull<Float>()
    private var secondHandLength by Delegates.notNull<Float>()

    private fun initClock() {
        padding = numeralSpacing + 50
        val min = height.coerceAtMost(width)
        radius = min / 2.2f - padding
        handTruncation = min / 20
        hourHandTruncation = min / 7
        paint = Paint()
        isInit = true
    }

    override fun onDraw(canvas: Canvas) {
        if (!isInit) {
            initClock()
        }
        canvas.drawColor(Color.WHITE)
        drawCircle(canvas)
        drawClockStroke(canvas)
        drawHands(canvas)
        postInvalidateDelayed(500)
        invalidate()
    }

    private fun drawHand(canvas: Canvas, loc: Double, arrowType: ClockArrow) {
        val angle = Math.PI * loc / 30 - Math.PI / 2
        val baseLength = height.coerceAtMost(width)
        val handLength = when (arrowType) {
            ClockArrow.HOUR -> if (hourHandLength == -1f) baseLength * 0.25f else hourHandLength
            ClockArrow.MINUTE -> if (minuteHandLength == -1f) baseLength * 0.3f else minuteHandLength
            ClockArrow.SECOND -> if (secondHandLength == -1f) baseLength * 0.35f else secondHandLength
        }
        val tailHand = 60
        canvas.drawLine(
            width / 2f - (cos(angle) * tailHand).toFloat(),
            height / 2f - (sin(angle) * tailHand).toFloat(),
            (width / 2 + cos(angle) * handLength).toFloat(),
            (height / 2 + sin(angle) * handLength).toFloat(),
            paint
        )
    }

    private fun drawHands(canvas: Canvas) {
        val c = Calendar.getInstance()
        var hour = c[Calendar.HOUR_OF_DAY].toFloat()
        hour = if (hour > 12) hour - 12 else hour
        paint.strokeWidth = 15f
        paint.color = secondColor
        drawHand(canvas, c[Calendar.SECOND].toDouble(), ClockArrow.SECOND)
        paint.strokeWidth = 20f
        paint.color = minuteColor
        drawHand(canvas, c[Calendar.MINUTE].toDouble(), ClockArrow.MINUTE)
        paint.strokeWidth = 27f
        paint.color = hourColor
        drawHand(canvas, ((hour + c[Calendar.MINUTE] / 60) * 5f).toDouble(), ClockArrow.HOUR)
    }

    private fun drawClockStroke(canvas: Canvas) {
        paint.textSize = fontSize.toFloat()
        for (number in numbers) {
            paint.strokeWidth = 20f
            paint.color = Color.BLACK

            val angle = Math.PI / 6 * (number - 3)
            val x = (width / 2 + cos(angle) * radius).toFloat()
            val y = (height / 2 + sin(angle) * radius).toFloat()
            val xEnd = (width / 2 + cos(angle) * (radius + 40)).toFloat()
            val yEnd = (height / 2 + sin(angle) * (radius + 40)).toFloat()
            canvas.drawLine(x, y, xEnd, yEnd, paint)
        }
    }

    private fun drawCircle(canvas: Canvas) {
        paint.reset()
        paint.color = Color.BLACK
        paint.strokeWidth = 20f
        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true
        canvas.drawCircle(width / 2f, height / 2f, radius + padding - 10f, paint)
    }

    init {
        if (attrs != null) {
            initAttributes(attrs, defStyleAttr)
        }
    }

    private fun initAttributes(
        attributesSet: AttributeSet?, defStyleAttr: Int
    ) {
        val typedArray: TypedArray = context.obtainStyledAttributes(
            attributesSet,
            R.styleable.CoolGraphView,
            defStyleAttr,
            0
        )
        hourColor = typedArray.getColor(R.styleable.CoolGraphView_hourHandColor, Color.MAGENTA)
//        minuteColor = typedArray.getColor(R.styleable.AnalogClockView_minuteHandColor, Color.YELLOW)
//        secondColor = typedArray.getColor(R.styleable.AnalogClockView_secondHandColor, Color.GREEN)
//        hourHandLength = typedArray.getDimension(R.styleable.AnalogClockView_hourHandLength, -1f)
//        minuteHandLength =
//            typedArray.getDimension(R.styleable.AnalogClockView_minuteHandLength, -1f)
//        secondHandLength =
//            typedArray.getDimension(R.styleable.AnalogClockView_secondHandLength, -1f)
        typedArray.recycle()
    }
}

enum class ClockArrow {
    HOUR, MINUTE, SECOND
}
