package com.xy.android.sdk.event

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import com.xy.android.sdk.BackActivity
import com.xy.android.sdk.databinding.ActivityEventDispatchBinding

class EventDispatchActivity : BackActivity() {

    private lateinit var binding: ActivityEventDispatchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEventDispatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(TAG, "EventDispatchActivity dispatchTouchEvent ACTION_DOWN")
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d(TAG, "EventDispatchActivity dispatchTouchEvent ACTION_MOVE")
            }
            MotionEvent.ACTION_UP -> {
                Log.d(TAG, "EventDispatchActivity dispatchTouchEvent ACTION_UP")
            }
            MotionEvent.ACTION_CANCEL -> {
                Log.d(TAG, "EventDispatchActivity dispatchTouchEvent ACTION_CANCEL")
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(TAG, "EventDispatchActivity onTouchEvent ACTION_DOWN")
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d(TAG, "EventDispatchActivity onTouchEvent ACTION_MOVE")
            }
            MotionEvent.ACTION_UP -> {
                Log.d(TAG, "EventDispatchActivity onTouchEvent ACTION_UP")
            }
            MotionEvent.ACTION_CANCEL -> {
                Log.d(TAG, "EventDispatchActivity onTouchEvent ACTION_CANCEL")
            }
        }
        return super.onTouchEvent(event)
    }

    companion object {

        const val TAG = "EventDispatch"
    }
}