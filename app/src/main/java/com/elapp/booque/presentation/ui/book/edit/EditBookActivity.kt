package com.elapp.booque.presentation.ui.book.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elapp.booque.R
import com.elapp.booque.databinding.ActivityEditBookBinding
import com.elapp.booque.presentation.ui.book.BookViewModel
import com.elapp.booque.utils.global.factory.ViewModelFactory
import com.elapp.booque.utils.network.NetworkState

class EditBookActivity : AppCompatActivity() {

    private val viewModel by lazy {
        val factory = ViewModelFactory.getInstance()
        ViewModelProvider(this, factory).get(BookViewModel::class.java)
    }

    private var _activityEditBookBinding: ActivityEditBookBinding? = null
    private val binding get() = _activityEditBookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityEditBookBinding = ActivityEditBookBinding.inflate(layoutInflater)
        setContentView(_activityEditBookBinding?.root)

        val id = intent.getIntExtra("book_id",0)

        loadBookDetail(id)
    }

    private fun loadBookDetail(bookId: Int) {
        viewModel.getDetailBuku(bookId).observe(this, Observer {
            binding?.edtNamaBuku?.setText(it.bookName)
            binding?.edtDeskripsi?.setText(it.description)
            binding?.edtAlamat?.setText(it.address)
            when (it.categoryName) {
                "Novel" -> binding?.spinnerCategory?.setSelection(0)
                "Komik" -> binding?.spinnerCategory?.setSelection(1)
                "Buku Pelajaran" -> binding?.spinnerCategory?.setSelection(2)
                "Sejarah" -> binding?.spinnerCategory?.setSelection(3)
                "Dongeng" -> binding?.spinnerCategory?.setSelection(4)
                "Kamus" -> binding?.spinnerCategory?.setSelection(5)
                "Cergam" -> binding?.spinnerCategory?.setSelection(6)
            }
            binding?.edtPenulis?.setText(it.author)
            binding?.edtYear?.setText("" + it.year)
            binding?.edtPenerbit?.setText(it.publisher)
        })
        viewModel.networkState.observe(this, Observer {
            when(it) {
                NetworkState.LOADING -> isLoading(true)
                NetworkState.LOADED -> isLoading(false)
            }
        })
    }

    private fun isLoading(status: Boolean) {
        if (!status) {
            binding?.backgroundDim?.visibility = View.INVISIBLE
            binding?.progressBar?.visibility = View.INVISIBLE
        } else {
            binding?.backgroundDim?.visibility = View.VISIBLE
            binding?.progressBar?.visibility = View.VISIBLE
        }
    }

}