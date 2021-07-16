package com.example.dinner2u

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_discover.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class Discover : AppCompatActivity() {

    private lateinit var detector: GestureDetectorCompat
    var downX:Float = 0.0f
    var upX:Float = 0.0f

    val swipeDistance = 500

    //variables para uploader imagenes
    lateinit var storage:FirebaseStorage
    lateinit var restaurantList:ArrayList<Restaurant>
    lateinit var db:FirebaseFirestore
    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover)

        storage = Firebase.storage
        restaurantList = arrayListOf()
        db = FirebaseFirestore.getInstance()
        getRestaurants()

        scrollView.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        run { downX = event.x }
                        run {
                            upX = event.x
                            val deltaX: Float = downX - upX
                            if (Math.abs(deltaX) > 0) {
                                return if (deltaX >= swipeDistance) {
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
                            return if (deltaX >= swipeDistance) {
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


    //funcion para descargar imagenes
    fun downloadImages(index: Int) {
        var imageRef = storage.reference
        val restaurant = restaurantList[index]
        var imagenPrincipalPath = "Restuarantes/" + restaurant.id + "/restaurant.jpeg"
        var foto1Path = "Restuarantes/" + restaurant.id + "/foto1.jpeg"
        var foto2Path = "Restuarantes/" + restaurant.id + "/foto2.jpeg"
        var foto3Path = "Restuarantes/" + restaurant.id + "/foto3.jpeg"
        var foto4Path = "Restuarantes/" + restaurant.id + "/foto4.jpeg"

        downloadImage(imagenPrincipalPath, imagenPrincipal)
        downloadImage(foto1Path, imagen1)
        downloadImage(foto2Path, imagen2)
        downloadImage(foto3Path, imagen3)
        downloadImage(foto4Path, imagen4)

    }

    fun downloadImage(imagePath: String, imageV: ImageView) = CoroutineScope(Dispatchers.IO).launch {
        var imageRef = storage.reference
        try {
            val maxDownloadSize = 5L * 1024 * 1024
            val bytes = imageRef.child(imagePath).getBytes(maxDownloadSize).await()
            val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            withContext(Dispatchers.Main) {
                imageV.setImageBitmap(bmp)
            }
        } catch(e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@Discover, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }


    fun updateText(index:Int) {
        val restaurant = restaurantList[index]
        restaurantName.text = restaurant.nombre
        restaurantCategory.text = restaurant.categoria
        restaurantDescription.text = restaurant.descripcion
    }



    private fun getRestaurants() {
        db = FirebaseFirestore.getInstance()
        db.collection("restaurants")
            .addSnapshotListener(object: EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Toast.makeText(this@Discover, error.message, Toast.LENGTH_LONG).show()
                    } else {
                        for (document: DocumentChange in value?.documentChanges!!) {
                            if (document.type == DocumentChange.Type.ADDED) {
                                restaurantList.add(document.document.toObject(Restaurant::class.java))
                            }
                        }
                    }
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
        if (count <= (restaurantList.size - 1)) {
            count++
            downloadImages(count)
            updateText(count)
        }
    }

    internal fun onSwipeRight() {
        if (count > 0) {
            count--
            downloadImages(count)
            updateText(count)
        }

    }



}