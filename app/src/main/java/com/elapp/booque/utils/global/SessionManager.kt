package com.elapp.booque.utils.global

import android.content.Context
import android.content.SharedPreferences
import com.elapp.booque.data.entity.Credential
import com.elapp.booque.data.entity.login.User
import com.elapp.booque.utils.SharedPreferencesKey.KEY_ADDRESS
import com.elapp.booque.utils.SharedPreferencesKey.KEY_CITY_ID
import com.elapp.booque.utils.SharedPreferencesKey.KEY_EMAIL
import com.elapp.booque.utils.SharedPreferencesKey.KEY_FULL_NAME
import com.elapp.booque.utils.SharedPreferencesKey.KEY_LOGIN
import com.elapp.booque.utils.SharedPreferencesKey.KEY_PHONE
import com.elapp.booque.utils.SharedPreferencesKey.KEY_PROVINCE_ID
import com.elapp.booque.utils.SharedPreferencesKey.KEY_USER_ID
import com.elapp.booque.utils.SharedPreferencesKey.USER_PREFS_NAME

class SessionManager(context: Context) {
    private var prefs: SharedPreferences = context.applicationContext.getSharedPreferences(USER_PREFS_NAME, Context.MODE_PRIVATE)

    /*Function save user data to shared preferences*/
    fun saveOAuth(user: User, credential: Credential) {
        val editor = prefs.edit()
        editor.putString(KEY_USER_ID, user.userId.toString())
        editor.putString(KEY_EMAIL, credential.email)
        editor.putString(KEY_FULL_NAME, user.fullName)
        editor.putString(KEY_ADDRESS, user.address)
        editor.putString(KEY_PHONE, user.phone)
        editor.putString(KEY_CITY_ID, user.cityId.toString())
        editor.putString(KEY_PROVINCE_ID, user.provinceId.toString())
        editor.putBoolean(KEY_LOGIN, true)
        editor.apply()
    }

}