package com.elapp.booque.presentation.ui.splash

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import com.elapp.booque.MainActivity
import com.elapp.booque.databinding.ActivitySplashBinding
import com.elapp.booque.presentation.ui.account.FormActivity
import com.elapp.booque.presentation.ui.appintro.IntroActivity
import com.elapp.booque.utils.SharedPreferencesKey.KEY_LOGIN
import com.elapp.booque.utils.SharedPreferencesKey.KEY_isIntroOpened
import com.elapp.booque.utils.SharedPreferencesKey.PREFS_NAME
import com.elapp.booque.utils.SharedPreferencesKey.USER_PREFS_NAME
import timber.log.Timber
import java.lang.ref.WeakReference

class SplashActivity : AppCompatActivity() {

    private var _activitySplashBinding: ActivitySplashBinding? = null
    private val binding get() = _activitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        _activitySplashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(_activitySplashBinding?.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val task = MyAsyncTask(this)
        task.execute()
    }

    companion object {
        class MyAsyncTask internal constructor(private val context: SplashActivity): AsyncTask<Void, Void, Void>() {

            private val activityReference : WeakReference<SplashActivity> = WeakReference(context)

            override fun onPreExecute() {
                val activity = activityReference.get()
                if (activity == null || activity.isFinishing) return
            }

            override fun onPostExecute(result: Void?) {
                val activity = activityReference.get()
                if (activity == null || activity.isFinishing) return

                with(activity) {
                    if (isIntroOpened() && isLogin()){
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }else if (isIntroOpened() && !isLogin()){
                        startActivity(Intent(this, FormActivity::class.java))
                        finish()
                    } else {
                        startActivity(Intent(this, IntroActivity::class.java))
                        finish()
                    }
                }
            }

            override fun doInBackground(vararg p0: Void?) : Void? {
                try{
                    Thread.sleep(3000)
                }catch (e: Exception) {
                    Timber.e("Error ${e.message.toString()}")
                }
                return null
            }
        }
    }

    private fun isIntroOpened(): Boolean {
        val pref = applicationContext.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        return pref.getBoolean(KEY_isIntroOpened, false)
    }

    private fun isLogin(): Boolean {
        val pref = applicationContext.getSharedPreferences(USER_PREFS_NAME, Context.MODE_PRIVATE)
        return pref.getBoolean(KEY_LOGIN, false)
    }

}