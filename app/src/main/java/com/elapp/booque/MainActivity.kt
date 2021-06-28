package com.elapp.booque

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.elapp.booque.databinding.ActivityMainBinding
import com.elapp.booque.presentation.ui.book.BookViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: BookViewModel

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