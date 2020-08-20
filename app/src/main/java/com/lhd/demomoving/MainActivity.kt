package com.lhd.demomoving

import android.annotation.SuppressLint
import android.graphics.PointF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Math.abs

class MainActivity : AppCompatActivity() {
    private val touchSlop by lazy {
        ViewConfiguration.get(this).scaledTouchSlop
    }
    private var isMoving = false
    private val pointDown = PointF()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvMove.setOnClickListener {
            Toast.makeText(this, "Click View", Toast.LENGTH_SHORT).show()
        }
        tvMove.setOnTouchListener { p0, motionEvent ->
            when (motionEvent.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    pointDown.set(motionEvent.x, motionEvent.y)
                    loge("Action down: $pointDown")
                    false
                }
                MotionEvent.ACTION_MOVE -> {
                    loge("Action MOve Before: (${tvMove.x},${tvMove.y}")
                    val disX = motionEvent.x - pointDown.x
                    val disY = motionEvent.y - pointDown.y
                    if (isMoving) {
                        tvMove.x += disX.toInt()
                        tvMove.y += disY.toInt()
                        loge("Action MOve After: (${tvMove.x},${tvMove.y}")
                        //pointDown.set(tvMove.x, tvMove.y)
                        true
                    } else {
                        if (abs(disX) >= touchSlop || abs(disY) >= touchSlop) {
                            isMoving = true
                            true
                        } else {
                            false
                        }
                    }
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    loge("Action Cancel")
                    if (isMoving) {
                        isMoving = false
                        true
                    } else {
                        false
                    }
                }
                else -> {
                    loge("Other ACtion: ${motionEvent.actionMasked}")
                    false
                }
            }
        }
    }

    private fun loge(msg: String) {
        Log.e("APPLOG", msg)
    }
}