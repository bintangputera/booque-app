package com.elapp.booque.utils.global

import android.content.Context
import android.content.SharedPreferences
import com.elapp.booque.data.entity.Credential
import com.elapp.booque.data.entity.login.User
import com.elapp.booque.utils.SharedPreferencesKey.KEY_ADDRESS
import com.elapp.booque.utils.SharedPreferencesKey.KEY_CITY_ID
import com.elapp.booque.utils.SharedPreferencesKey.KEY_CITY_NAME
import com.elapp.booque.utils.SharedPreferencesKey.KEY_EMAIL
import com.elapp.booque.utils.SharedPreferencesKey.KEY_FULL_NAME
import com.elapp.booque.utils.SharedPreferencesKey.KEY_LOGIN
import com.elapp.booque.utils.SharedPreferencesKey.KEY_PHONE
import com.elapp.booque.utils.SharedPreferencesKey.KEY_PROVINCE_ID
import com.elapp.booque.utils.SharedPreferencesKey.KEY_PROVINCE_NAME
import com.elapp.booque.utils.SharedPreferencesKey.KEY_USER_ID
import com.elapp.booque.utils.SharedPreferencesKey.LOCATION_PREFS_NAME
import com.elapp.booque.utils.SharedPreferencesKey.USER_PREFS_NAME

class SessionManager(context: Context) {
    private var prefs: SharedPreferences = context.applicationContext.getSharedPreferences(USER_PREFS_NAME, Context.MODE_PRIVATE)
    private var locPrefs: SharedPreferences = context.applicationContext.getSharedPreferences(
        LOCATION_PREFS_NAME, Context.MODE_PRIVATE)

    /*Function save user data to shared preferences*/
    fun saveOAuth(user: User, email: String) {
        val editor = prefs.edit()
        editor.putInt(KEY_USER_ID, user.userId)
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_FULL_NAME, user.fullName)
        editor.putString(KEY_ADDRESS, user.address)
        editor.putString(KEY_PHONE, user.phone)
        editor.putInt(KEY_CITY_ID, user.cityId)
        editor.putInt(KEY_PROVINCE_ID, user.provinceId)
        editor.putBoolean(KEY_LOGIN, true)
        editor.apply()
    }

    fun saveUpdate(userId: Int, fullName: String, address: String, phone: String, cityId: Int, provinceId: Int) {
        val editor = prefs.edit()
        editor.putInt(KEY_USER_ID, userId)
        editor.putString(KEY_FULL_NAME, fullName)
        editor.putString(KEY_ADDRESS, address)
        editor.putString(KEY_PHONE, phone)
        editor.putInt(KEY_CITY_ID, cityId)
        editor.putInt(KEY_PROVINCE_ID, provinceId)
        editor.apply()
    }

    fun saveProvince(provinceName: String) {
        val editor = locPrefs.edit()
        editor.putString(KEY_PROVINCE_NAME, provinceName)
        editor.apply()
    }

    fun saveCity(cityName: String) {
        val editor = locPrefs.edit()
        editor.putString(KEY_CITY_NAME, cityName)
        editor.apply()
    }

    val provinceName = locPrefs.getString(KEY_PROVINCE_NAME, null)
    val cityName = locPrefs.getString(KEY_CITY_NAME, null)
    val userId = prefs.getInt(KEY_USER_ID, 0)
    val fullName = prefs.getString(KEY_FULL_NAME, null)
    val email = prefs.getString(KEY_EMAIL, null)
    val cityId = prefs.getInt(KEY_CITY_ID, 0)
    val provinceId = prefs.getInt(KEY_PROVINCE_ID,0)

}