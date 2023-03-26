package com.xy.android.sdk.event

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.FrameLayout
import org.jetbrains.anko.dip

class EventDispatchLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val paint by lazy {
        Paint().apply {
            color = Color.BLACK
            textSize = context.dip(12f).toFloat()
        }
    }

    init {
        setWillNotDraw(false)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawText("$tag 111", 100f, 100f, paint)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(
                    EventDispatchActivity.TAG,
                    "$TAG$tag dispatchTouchEvent ACTION_DOWN"
                )
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d(
                    EventDispatchActivity.TAG,
                    "$TAG$tag dispatchTouchEvent ACTION_MOVE"
                )
            }
            MotionEvent.ACTION_UP -> {
                Log.d(
                    EventDispatchActivity.TAG,
                    "$TAG$tag dispatchTouchEvent ACTION_UP"
                )
            }
            MotionEvent.ACTION_CANCEL -> {
                Log.d(
                    EventDispatchActivity.TAG,
                    "$TAG$tag dispatchTouchEvent ACTION_CANCEL"
                )
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(
                    EventDispatchActivity.TAG,
                    "$TAG$tag onInterceptTouchEvent ACTION_DOWN"
                )
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d(
                    EventDispatchActivity.TAG,
                    "$TAG$tag onInterceptTouchEvent ACTION_MOVE"
                )
            }
            MotionEvent.ACTION_UP -> {
                Log.d(
                    EventDispatchActivity.TAG,
                    "$TAG$tag onInterceptTouchEvent ACTION_UP"
                )
            }
            MotionEvent.ACTION_CANCEL -> {
                Log.d(
                    EventDispatchActivity.TAG,
                    "$TAG$tag onInterceptTouchEvent ACTION_CANCEL"
                )
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(EventDispatchActivity.TAG, "$TAG$tag onTouchEvent ACTION_DOWN")
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d(EventDispatchActivity.TAG, "$TAG$tag onTouchEvent ACTION_MOVE")
            }
            MotionEvent.ACTION_UP -> {
                Log.d(EventDispatchActivity.TAG, "$TAG$tag onTouchEvent ACTION_UP")
            }
            MotionEvent.ACTION_CANCEL -> {
                Log.d(EventDispatchActivity.TAG, "$TAG$tag onTouchEvent ACTION_CANCEL")
            }
        }
        return super.onTouchEvent(event)
    }

    companion object {

        private const val TAG = "EventDispatchLayout"
    }
}