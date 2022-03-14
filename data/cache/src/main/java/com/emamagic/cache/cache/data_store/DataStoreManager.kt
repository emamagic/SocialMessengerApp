package com.emamagic.cache.cache.data_store

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.emamagic.cache.User
import com.emamagic.cache.cache.CacheInitializer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException

val store: DataStore<Preferences> by lazy { CacheInitializer.getStore() }


object StoreKeys {
    val SORT_ORDER = stringPreferencesKey("sort_order")
    val SHOW_COMPLETED = booleanPreferencesKey("show_completed")
}


private val userStore: DataStore<User> by lazy { CacheInitializer.getUserStore() }

val getUser: Flow<User> = userStore.data.catch { exception ->
    if (exception is IOException) {
        Log.e("Error", exception.message.toString())
        emit(User.getDefaultInstance())
    } else {
        throw exception
    }
}

suspend fun setUser(
    id: String,
    username: String,
    firstName: String,
    lastName: String,
    nickName: String,
    phoneNumber: String,
    avatarHash: String,
    email: String
) {
    userStore.updateData { preference ->
        preference.toBuilder()
            .setId(id)
            .setUsername(username)
            .setFirstName(firstName)
            .setLastName(lastName)
            .setNickName(nickName)
            .setPhoneNumber(phoneNumber)
            .setAvatarHash(avatarHash)
            .setEmail(email)
            .build()
    }

}






