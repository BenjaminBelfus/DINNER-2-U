package com.example.dinner2u.models.models.models.models

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_discover.*
import com.example.dinner2u.R
import com.example.dinner2u.models.models.models.database.categories.CategoryModel
import com.example.dinner2u.models.models.models.database.restaurants.RestaurantModel
import com.example.dinner2u.models.models.models.database.restaurants.RestaurantsDBHelper
import com.squareup.picasso.Picasso
import kotlin.math.abs


class DiscoverActivity : AppCompatActivity() {

    //Variables used for firebase
    private lateinit var storage:FirebaseStorage
    private lateinit var db:FirebaseFirestore

    //Variables used for local database
    private lateinit var restaurantdbhelper: RestaurantsDBHelper
    private val restaurantListByCategory = arrayListOf<RestaurantModel>()
    private lateinit var category: CategoryModel

    //Variables used for swipe left and swipe right function
    private var downX:Float = 0.0f
    private var upX:Float = 0.0f
    private val swipeDistance = 600

    //Variable used for keeping track of the restaurant in restauratnListByCategory when swipe left or right
    private var index = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover)

        //Firebase
        db = FirebaseFirestore.getInstance()
        storage = Firebase.storage

        //getRestaurants()
        val bundle: Bundle? = intent.extras
        category = bundle?.getSerializable("category") as CategoryModel
        categoryLabel.text = category.name


        restaurantdbhelper = RestaurantsDBHelper(this)


//        How to add restaurants:
//        val restaurant = RestaurantModel(UUID.randomUUID().toString(), "THE SANDWICH", R.string.sushi_description.toString(), category.id, "https://cdn.vox-cdn.com/thumbor/3OdmXqvbG3rq2lKOagEjaExkpis=/0x0:2048x1588/1200x800/filters:focal(861x631:1187x957)/cdn.vox-cdn.com/uploads/chorus_image/image/68617826/134301527_150986323476283_8160139784797337975_o.0.jpg",
//        "https://www.justonecookbook.com/wp-content/uploads/2020/01/Sushi-Rolls-Maki-Sushi-%E2%80%93-Hosomaki-1117-I.jpg", "https://i2.wp.com/www.eatthis.com/wp-content/uploads/2020/07/assorted-sushi.jpg?fit=1200%2C879&ssl=1",
//                    "https://www.goodforyouglutenfree.com/wp-content/uploads/2017/02/Gluten-free-sushi-rolls-header.jpg")

        getRestaurants()

//        restaurantdbhelper.insertRestaurant(restaurant)
//        restaurantListByCategory.add(restaurant)


//      Swipe left, Swipe right function
        scrollView.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        run { downX = event.x }
                    }
                    MotionEvent.ACTION_UP -> {
                        upX = event.x
                        val deltaX: Float =  downX - upX
                        if (abs(deltaX) > 0 && abs(deltaX) > swipeDistance) {
                            return if (downX > upX) {
                                index++
                                onLeftSwipe2()
                                true
                            } else {
                                index--
                                onSwipeRight2()
                                true
                            }
                        }
                    }
                }
                return false
            }
        })

        discoverMenuButton.setOnClickListener{
            val intent = Intent(this, DetailMenuActivity::class.java)
            startActivity(intent)
        }
    }

    fun getRestaurants() {
        val restaurants = restaurantdbhelper.readAllRestaurants(category.id)
        restaurantListByCategory.addAll(restaurants)
        updateRestaurants(index)
    }

    internal fun onLeftSwipe2() {
        if (index <= (restaurantListByCategory.size - 1)) {
            updateRestaurants(index)
        }
    }

    internal fun onSwipeRight2() {
        if (index > 0) {
            updateRestaurants(index)
        }
    }

    fun updateRestaurants(index: Int) {
        val restaurant = restaurantListByCategory[index]
        Picasso.with(this).load(restaurant.mainpicture).into(imagenPrincipal)
        restaurantName.setText(restaurant.name)

        Picasso.with(this).load(restaurant.firstpicture).into(discoverImage1)
        Picasso.with(this).load(restaurant.secondpicture).into(discoverImage2)
        Picasso.with(this).load(restaurant.thirdpicture).into(discoverImage3)

        discoverDescription.text = restaurant.description
    }





//    TODO ESTO ES FIREBASE
//    funcion para igualar variables de fotos a sus path
//    fun downloadImages(index: Int) {
//        val restaurant = restaurantList[index]
//        var imagenPrincipalPath = "Restuarantes/" + restaurant.id + "/restaurant.jpeg"
//
//
//        var foto1Path = "Restuarantes/" + restaurant.id + "/foto1.jpeg"
//        var foto2Path = "Restuarantes/" + restaurant.id + "/foto2.jpeg"
//        var foto3Path = "Restuarantes/" + restaurant.id + "/foto3.jpeg"
//        var foto4Path = "Restuarantes/" + restaurant.id + "/foto4.jpeg"
//
//        downloadImage(imagenPrincipalPath, imagenPrincipal)
//        downloadImage(foto1Path, imagen1)
//        downloadImage(foto2Path, imagen2)
//        downloadImage(foto3Path, imagen3)
//        downloadImage(foto4Path, imagen4)
//
//    }
//
//    //funcion para descargar cada imagen y setearla a su respectivo ID en el layout
//    fun downloadImage(imagePath: String, imageV: ImageView) = CoroutineScope(Dispatchers.IO).launch {
//        var imageRef = storage.reference
//        try {
//            val maxDownloadSize = 5L * 1024 * 1024
//            val bytes = imageRef.child(imagePath).getBytes(maxDownloadSize).await()
//            val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
//            withContext(Dispatchers.Main) {
//                imageV.setImageBitmap(bmp)
//            }
//        } catch(e: Exception) {
//            withContext(Dispatchers.Main) {
//                Toast.makeText(this@DiscoverActivity, e.message, Toast.LENGTH_LONG).show()
//            }
//        }
//    }
//
//
//    fun updateText(index:Int) {
//        val restaurant = restaurantList[index]
//        restaurantName.text = restaurant.nombre
//        restaurantCategory.text = restaurant.categoria
//        restaurantDescription.text = restaurant.descripcion
//    }


//
//    private fun getRestaurants() {
//        db = FirebaseFirestore.getInstance()
//        db.collection("restaurants")
//            .addSnapshotListener(object: EventListener<QuerySnapshot> {
//                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
//                    if (error != null) {
//                        Toast.makeText(this@DiscoverActivity, error.message, Toast.LENGTH_LONG).show()
//                    } else {
//                        for (document: DocumentChange in value?.documentChanges!!) {
//                            if (document.type == DocumentChange.Type.ADDED) {
//                                restaurantList.add(document.document.toObject(`RestaurantDataSource(firebase)`::class.java))
//                            }
//                        }
//                    }
//                }
//
//            })
//    }

//    internal fun onLeftSwipe() {
//        if (index <= (restaurantList.size - 1)) {
//            index++
//            downloadImages(index)
//            updateText(index)
//        }
//    }

//    internal fun onSwipeRight() {
//        if (index > 0) {
//            index--
//            downloadImages(index)
//            updateText(index)
//        }
//    }


}