package com.example.dinner2u.models.models.models.models.managers

import com.example.dinner2u.models.models.models.database.users.UserModel
import com.google.firebase.firestore.auth.User

object DataManager {
    var currentUser: UserModel = UserModel("", "", "", "", "", "", "")
    private set

    fun setCurrentUser(user: UserModel) {
        currentUser = user
    }

    fun getCurrentUserID(): String {
        return currentUser.id
    }
}