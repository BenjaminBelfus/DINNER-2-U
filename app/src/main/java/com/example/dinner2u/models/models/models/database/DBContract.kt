package com.example.dinner2u.models.models.models.database

import android.provider.BaseColumns

object DBContract {

    class UserEntry: BaseColumns {
        companion object {
            val TABLE_NAME = "users"
            val COLUMN_USER_ID = "userid"
            val COLUMN_NAME = "name"
            val COLUMN_LASTNAME = "lastname"
            val COLUMN_EMAIL = "email"
            val COLUMN_PASSWORD = "password"
            val COLUMN_DATE_CREATED = "datecreated"
            val COLUMN_MAIN_ADDRESS = "mainadress"
        }
    }

    class CategoryEntry: BaseColumns {
        companion object {
            val TABLE_NAME = "categories"
            val COLUMN_CATEGORY_ID = "categoryid"
            val COLUMN_CATEGORY_NAME = "categoryname"
        }
    }

    class RestaurantEntry: BaseColumns {
        companion object {
            val TABLE_NAME = "restaurants"
            val COLUMN_RESTAURANT_ID = "restaurantid"
            val COLUMN_RESTAURANT_NAME = "restaurantname"
            val COLUMN_RESTAURANT_DESCRIPTION = "restaurantdescription"
            val COLUMN_RESTAURANT_FOODCATEGORYID = "restaurantfoodcategoryid"
            val COLUMN_RESTAURANT_MAINPICTURE = "restaurantmainpicture"
            val COLUMN_RESTAURANT_FIRSTPICTURE = "restaurantfirstpicture"
            val COLUMN_RESTAURANT_SECONDPICTURE = "restaurantsecondpicture"
            val COLUMN_RESTAURANT_THIRDPICTURE = "restaurantthirdpicture"
        }
    }

    class MenuEntry: BaseColumns {
        companion object {
            val TABLE_NAME = "menus"
            val COLUMN_MENU_ID = "menuid"
            val COLUMN_RESTAURANT_ID = "restaurantid"
            val COLUMN_DISH_ID = "dishid"
        }
    }

    class DishEntry: BaseColumns {
        companion object {
            val TABLE_NAME = "dishes"
            val COLUMN_DISH_ID = "dishid"
            val COLUMN_DISH_NAME = "dishname"
            val COLUMN_DISH_PICTURE = "dishpicture"
            val COLUMN_DISH_DESCRIPTION = "dishdescription"
            val COLUMN_DISH_PRICE = "dishprice"
        }
    }

    class CartEntry: BaseColumns {
        companion object {
            val TABLE_NAME = "carts"
            val COLUMN_CART_ID = "id"
            val COLUMN_RESTAURANT_ID = "restaurantid"
            val COLUMN_DISH_ID = "dishid"
            val COLUMN_USER_ID = "userid"
        }
    }

    class CartItemEntry: BaseColumns {
        companion object {
            val COLUMN_RESTAURANT_NAME = "restaurantname"
            val COLUMN_DISH_NAME = "dishname"
            val COLUMN_DISH_PRICE = "dishprice"
            val COLUMN_USER_ADDRESS = "mainadress"
        }
    }
}