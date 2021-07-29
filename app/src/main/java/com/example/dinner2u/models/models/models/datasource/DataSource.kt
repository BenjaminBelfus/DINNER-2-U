package com.example.dinner2u.models.models.models.datasource

import com.example.dinner2u.models.models.models.dataclass.RestaurantPost

class DataSource {

    companion object{
        val temporalHashmap:HashMap<Int,ArrayList<String>> = HashMap<Int, ArrayList<String>>()

        fun createDataSet() {
            val list1 = ArrayList<String>()
            list1[0] = "https://www.papercitymag.com/wp-content/uploads/2018/06/MF-Sushi_interior_Photo-courtesy-of-MF-Sushi-Houston-Facebook.jpg"
            list1[1] = "Nobu"
            list1[2] = "Sushi"
            list1[3] = "https://i2.wp.com/topsushimaker.com/wp-content/uploads/2019/04/2742_top.jpeg?fit=960%2C581&ssl=1"
            list1[4] = "https://images.japancentre.com/recipes/pics/18/main/makisushi.jpg?1557308201"
            list1[5] = "https://purewows3.imgix.net/images/articles/2021_01/sushi-for-beginners-CAT.jpg?auto=format,compress&cs=strip"
            list1[6] = "@string/sushi_description"

            temporalHashmap.put(1, list1)

            RestaurantPost(
                temporalHashmap
            )

            val list2 = ArrayList<String>()
            list1[0] = "https://www.harrods.com/BWStaticContent/50000/3e835540-215d-45ed-b82f-106388facc15_m-restaurants-gordon-ramsay-burger-hero.jpg"
            list1[1] = "THE NEIGHBORHOOD'S SANDWICH"
            list1[2] = "Hamburguers"
            list1[3] = "https://www.gordonramsayrestaurants.com/assets/Uploads/_resampled/CroppedFocusedImage121578650-50-Gordon-Ramsay-Brittania-Burger-Tablet.png"
            list1[4] = "https://www.harrods.com/BWStaticContent/50000/31296d27-7215-45ec-9b02-d326f8ecd5c9_d-gallery-landscape-truffle-fries.jpg"
            list1[5] = "https://pbs.twimg.com/media/Eoad9W9XMAE40_q.jpg"
            list1[6] = "string/burguer_description"

            temporalHashmap.put(2, list2)


            RestaurantPost(
                temporalHashmap
            )
        }
    }
}