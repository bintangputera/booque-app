package com.elapp.booque.presentation.ui.account

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.elapp.booque.databinding.ActivityFormBinding
import com.elapp.booque.utils.global.factory.ViewModelFactory

class FormActivity : AppCompatActivity() {

    lateinit var factory: ViewModelFactory

    lateinit var viewModel: FormViewModel
    lateinit var registerViewModel: RegisterViewModel

    private var _formActivityBinding : ActivityFormBinding? = null
    private val binding get() = _formActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _formActivityBinding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(_formActivityBinding?.root)

        viewModel = ViewModelProvider(this, factory).get(FormViewModel::class.java)
        registerViewModel = ViewModelProvider(this, factory).get(RegisterViewModel::class.java)
    }
}