package com.elapp.booque.presentation.ui.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.elapp.booque.databinding.ActivityFormBinding
import com.elapp.booque.utils.global.factory.FormViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FormActivity : AppCompatActivity() {

    @Inject
    lateinit var factoryForm: FormViewModelFactory

    lateinit var viewModel: FormViewModel
    lateinit var registerViewModel: RegisterViewModel

    private var _formActivityBinding : ActivityFormBinding? = null
    private val binding get() = _formActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _formActivityBinding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(_formActivityBinding?.root)

        viewModel = ViewModelProvider(this, factoryForm).get(FormViewModel::class.java)
        registerViewModel = ViewModelProvider(this, factoryForm).get(RegisterViewModel::class.java)

    }
}