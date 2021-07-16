package com.example.dinner2u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import kotlinx.android.synthetic.main.activity_discover.*
import kotlin.math.abs

class Discover2 : AppCompatActivity(), GestureDetector.OnGestureListener{

    lateinit var gestureDetector: GestureDetector
    var x1:Float = 0.0f
    var x2:Float = 0.0f

    companion object{
        const val MIN_DISTANCE = 500
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover2)

        gestureDetector = GestureDetector(this, this)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)

        when(event?.action) {
            0-> {
                x1 = event.x
            }
            1-> {
                x2 = event.x

                val valueX:Float = x2-x1
                if (abs(valueX) > MIN_DISTANCE) {
                    if(x2>x1) {
                        onSwipeRight()
                    }
                }

            }
        }
        return super.onTouchEvent(event)
    }


    fun onSwipeRight() {
        val intent = Intent(this, Discover::class.java)
        startActivity(intent)
    }









    override fun onDown(e: MotionEvent?): Boolean {
        //TODO("Not yet implemented")
        return false
    }

    override fun onShowPress(e: MotionEvent?) {
        //TODO("Not yet implemented")
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        //TODO("Not yet implemented")
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        //TODO("Not yet implemented")
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
        TODO("Not yet implemented")
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        //TODO("Not yet implemented")
        return false
    }

}