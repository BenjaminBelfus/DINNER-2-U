package com.example.dinner2u

import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import kotlinx.android.synthetic.main.activity_discover.*

class Discover : AppCompatActivity() {

    private lateinit var detector: GestureDetectorCompat
    var downX:Float = 0.0f
    var upX:Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover)

        scrollView.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        run { downX = event.x }
                        run {
                            upX = event.x
                            val deltaX: Float = downX - upX
                            if (Math.abs(deltaX) > 0) {
                                return if (deltaX >= 0) {
                                    onSwipeRight()
                                    true
                                } else {
                                    onLeftSwipe()
                                    true
                                }
                            }
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        upX = event.x
                        val deltaX: Float = downX - upX
                        if (Math.abs(deltaX) > 0) {
                            return if (deltaX >= 0) {
                                onLeftSwipe()
                                true
                            } else {

                                onSwipeRight()
                                true
                            }
                        }
                    }
                }
                return false
            }
        })
    }


    private fun onSwipeBottom() {
        Toast.makeText(this, "Bottom Swipe", Toast.LENGTH_LONG).show()
    }

    private fun onSwipeTop() {
        Toast.makeText(this, "Top Swipe", Toast.LENGTH_LONG).show()
    }

    internal fun onLeftSwipe() {
        val intent = Intent(this, Discover2::class.java)
        startActivity(intent)
    }

    internal fun onSwipeRight() {
    }


}