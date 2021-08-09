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
import java.util.*
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


        val bundle: Bundle? = intent.extras
        category = bundle?.getSerializable("category") as CategoryModel
        categoryLabel.text = category.name


        restaurantdbhelper = RestaurantsDBHelper(this)




//      How to add restaurants:
//      Sushi:
//        val restaurant = RestaurantModel(
//            UUID.randomUUID().toString(), "Nobu", "Pioneered by Chef Nobu Matsuhisa, the iconic Nobu menu is influenced by his years of studying Japanese cuisine in Tokyo and his extensive travels. The diverse celebration of flavors formed Nobu’s trademark ‘Nobu Style’, which is intrinsic to all Nobu menus worldwide. ", category.id, "https://cdn.vox-cdn.com/thumbor/3OdmXqvbG3rq2lKOagEjaExkpis=/0x0:2048x1588/1200x800/filters:focal(861x631:1187x957)/cdn.vox-cdn.com/uploads/chorus_image/image/68617826/134301527_150986323476283_8160139784797337975_o.0.jpg",
//        "https://www.justonecookbook.com/wp-content/uploads/2020/01/Sushi-Rolls-Maki-Sushi-%E2%80%93-Hosomaki-1117-I.jpg", "https://i2.wp.com/www.eatthis.com/wp-content/uploads/2020/07/assorted-sushi.jpg?fit=1200%2C879&ssl=1",
//                    "https://www.goodforyouglutenfree.com/wp-content/uploads/2017/02/Gluten-free-sushi-rolls-header.jpg")
//
//        restaurantdbhelper.insertRestaurant(restaurant)
//        restaurantListByCategory.add(restaurant)
//
////      Burger:
//        val burguer = RestaurantModel(UUID.randomUUID().toString(), "THE SANDWICH", "Championing some of the most incredible ingredients from amazing British providers, producers, artisans and growers, all cooked to absolute perfection and served with sensational sides, I guarantee this will be a burger experience like no other.",
//        category.id, "https://cdn.vox-cdn.com/thumbor/tqyZUJEn66jgFZytP4zOSIy1pxE=/0x0:1000x559/1200x800/filters:focal(420x199:580x359)/cdn.vox-cdn.com/uploads/chorus_image/image/61171553/Gordon_20Ramsay_20BurGR_2012-18-2012_207.0.0.1529597415.0.jpg",
//        "https://www.gordonramsayrestaurants.com/assets/Uploads/_resampled/CroppedFocusedImage121578650-50-Gordon-Ramsay-Brittania-Burger-Tablet.png", "https://www.hot-dinners.com/images/stories/blog/2020/ramseyburger2.jpg",
//        "https://www.caesars.com/content/scaffold_pages/restaurant/planet-hollywood/phv/en/ramsay-burgr/_jcr_content/cards/card/slides71.stdimg.hd.l.png/1580498100051.png")
//
//        restaurantdbhelper.insertRestaurant(burguer)
//        restaurantListByCategory.add(burguer)

////      Pizza:
//        val pizza = RestaurantModel(UUID.randomUUID().toString(), "Sliver", "We began SLIVER as a dream that centered around bringing the Bay Area to the next level in quality food and social awareness.", category.id,
//        "https://www.pointfranchise.co.uk/images/zoom/articles/pizza-restaurant-franchises.jpeg", "https://simpletexting.com/wp-content/uploads/2018/10/11-Pizza-Marketing-Ideas.jpeg", "https://previews.123rf.com/images/lenyvavsha/lenyvavsha1505/lenyvavsha150500109/39764920-hot-italian-pizza-with-rocket-salad-and-salami-vertical-view-from-above.jpg",
//            "http://embed.widencdn.net/img/beef/78foq693gs/exact/pizza-with-a-purpose-horizontal.tiff?keep=c&u=7fueml")
//
//        restaurantdbhelper.insertRestaurant(pizza)
//        restaurantListByCategory.add(pizza)

////      Breakfast:
//        val breakfast = RestaurantModel(UUID.randomUUID().toString(), "Social Club", "Inspired, contemporary Korean American cuisine located in the heart of Berkeley’s University Avenue. Serving innovative Asian dinners and a creative American breakfast and brunch menu with a touch of Asian influence. We combine top notch chefs, top-quality locally-sourced organic ingredients and a relaxed, spacious atmosphere to create a haven for people who love great food and drinks.",
//        category.id, "https://www.berkeleyside.org/wp-content/uploads/2017/01/Berkeley-Social-Club-BI6A5607.jpg", "https://s3-media0.fl.yelpcdn.com/bphoto/Apt-6Daczl4zamJkl_WbrQ/258s.jpg",
//        "https://img.particlenews.com/image.php?type=thumbnail_580x000&url=3yB0Ct_0Z2qjnhN00", "https://resizer.otstatic.com/v2/photos/wide-huge/1/25018014.jpg")
//
//        restaurantdbhelper.insertRestaurant(breakfast)
//        restaurantListByCategory.add(breakfast)

////      Sushi 2:
//        val sushi2 = RestaurantModel(UUID.randomUUID().toString(), "Tako Sushi", "Family owned and operated since 2005.We provide fresh, quality sushi and a fun place to hang out. Voted best of YOLO in the sushi restaurant.",
//        category.id, "https://cdn.vox-cdn.com/thumbor/zyJZKOO7EJD3qlMyLBQCtS-DYPw=/66x0:1132x800/1200x800/filters:focal(66x0:1132x800)/cdn.vox-cdn.com/uploads/chorus_image/image/48603777/Naoki_interiors-2358.0.0.jpg",
//        "https://media.cntraveler.com/photos/5e31ffc40615da0008a91746/1:1/w_320%2Cc_limit/SushiGinzaOnodera-LosAngeles-2020-5.jpg", "https://images.fineartamerica.com/images/artworkimages/mediumlarge/1/nigiri-sushi-on-wood-vertical-susan-schmitz.jpg",
//        "https://s3.amazonaws.com/bucket.naoki-sushi.com/wp-content/themes/naoki/images/plated_sushi.jpg")
//
//        restaurantdbhelper.insertRestaurant(sushi2)
//        restaurantListByCategory.add(sushi2)


        getRestaurants()




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
            val restaurant = restaurantListByCategory[index]
            val intent = Intent(this, DetailMenuActivity::class.java).putExtra("restaurant", restaurant)
            startActivity(intent)
        }
    }

    fun getRestaurants() {
//      This if case is checking if the category pressed is the discover one, so it loads all restaurants and display them
        if (category.id == "a298c647-0a34-4bf6-881b-18100a413286") {
            val restaurants = restaurantdbhelper.readAllRestaurantForCategoryDiscover()
            restaurantListByCategory.addAll(restaurants)
            updateRestaurants(index)
            return
//      Else, it just loads the restaurants from that cateogry/
        } else {
            val restaurants = restaurantdbhelper.readAllRestaurantsFromCategory(category.id)
            restaurantListByCategory.addAll(restaurants)
            updateRestaurants(index)
        }
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





//    TO DO ESTO ES FIREBASE
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