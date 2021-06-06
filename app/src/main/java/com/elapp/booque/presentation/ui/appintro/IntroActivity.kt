package com.elapp.booque.presentation.ui.appintro

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.elapp.booque.R
import com.elapp.booque.databinding.ActivityIntroBinding
import com.elapp.booque.presentation.ui.onboarding.OnBoardingActivity
import com.elapp.booque.utils.SharedPreferencesKey.KEY_isIntroOpened
import com.elapp.booque.utils.SharedPreferencesKey.PREFS_NAME

class IntroActivity : AppCompatActivity() {

    private var _introActivityBinding: ActivityIntroBinding? = null
    private val binding get() = _introActivityBinding

    private lateinit var introViewPagerAdapter: IntroViewPagerAdapter
    private lateinit var btnAnim: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _introActivityBinding = ActivityIntroBinding.inflate(layoutInflater)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(_introActivityBinding?.root)

        supportActionBar?.hide()

        val mList = ArrayList<ScreenItem>()
        mList.add(ScreenItem("Saling Berbagi Tanpa Merugi", "Yuk Bagikan buku bekas kalian ke orang yang membutuhkan", R.drawable.intro_illustration_1))
        mList.add(ScreenItem("Saling terhubung hanya menggunakan Gadget kalian", "Make yourself expert your skill   by studying from mentors", R.drawable.intro_illustration_2))
        mList.add(ScreenItem("Easy Payment", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua, consectetur  consectetur adipiscing elit", R.drawable.intro_illustration_3))

        introViewPagerAdapter = IntroViewPagerAdapter(this, mList)
        binding?.screenViewpager?.adapter = introViewPagerAdapter
        binding?.screenViewpager?.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (position == mList.size - 1) {
                    loadLastScreen()
                }
            }

        })

        binding?.tabIndicator?.setupWithViewPager(binding?.screenViewpager)

        btnAnim = AnimationUtils.loadAnimation(applicationContext, R.anim.button_animation)

        binding?.btnGetStarted?.setOnClickListener {
            val intent = Intent(applicationContext, OnBoardingActivity::class.java)
            startActivity(intent)
            savePrefsData()
            finish()
        }

        binding?.tvSkip?.setOnClickListener{
            binding?.screenViewpager?.currentItem = mList.size
        }

    }

    private fun savePrefsData(){
        val pref = applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean(KEY_isIntroOpened, true)
        editor.apply()
    }

    private fun loadLastScreen() {
        binding?.btnGetStarted?.visibility = View.VISIBLE
        binding?.tvSkip?.visibility = View.INVISIBLE

        binding?.btnGetStarted?.animation = btnAnim
    }

}