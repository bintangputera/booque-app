package com.elapp.booque

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.elapp.booque.databinding.ActivityMainBinding
import com.elapp.booque.presentation.ui.account.FormViewModel
import com.elapp.booque.presentation.ui.account.RegisterViewModel
import com.elapp.booque.utils.global.factory.FormViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factoryForm: FormViewModelFactory

    lateinit var viewModel: FormViewModel
    lateinit var registerViewModel: RegisterViewModel

    private var _mainActivityBinding: ActivityMainBinding? = null
    private val binding get() = _mainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_mainActivityBinding?.root)
        binding?.btmNavigation?.setupWithNavController(
            fragment.findNavController()
        )
    }
}