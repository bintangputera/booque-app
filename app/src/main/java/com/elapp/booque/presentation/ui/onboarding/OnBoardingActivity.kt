package com.elapp.booque.presentation.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.elapp.booque.databinding.ActivityOnBoardingActivityBinding
import com.elapp.booque.presentation.ui.account.FormActivity
import com.elapp.booque.presentation.ui.account.RegisterFragment

class OnBoardingActivity : AppCompatActivity() {

    private var _activityOnBoardingBinding: ActivityOnBoardingActivityBinding? = null
    private val binding get() = _activityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityOnBoardingBinding = ActivityOnBoardingActivityBinding.inflate(layoutInflater)
        setContentView(_activityOnBoardingBinding?.root)

        supportActionBar?.hide()

        binding?.btnMulaiSekarang?.setOnClickListener {
            startActivity(Intent(applicationContext, FormActivity::class.java))
            finish()
        }
    }
}