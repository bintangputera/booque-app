package com.elapp.booque.presentation.ui.book.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.elapp.booque.R
import com.elapp.booque.databinding.ActivityBookDetailBinding
import com.elapp.booque.presentation.ui.book.BookViewModel
import com.elapp.booque.presentation.ui.book.edit.EditBookActivity
import com.elapp.booque.utils.global.NetworkAuthConf.BOOK_THUMBNAIL_BASE_URL
import com.elapp.booque.utils.global.SessionManager
import com.elapp.booque.utils.global.factory.ViewModelFactory
import com.elapp.booque.utils.network.NetworkState

class BookDetailActivity : AppCompatActivity() {

    private val viewModel by lazy {
        val factory = ViewModelFactory.getInstance()
        ViewModelProvider(this, factory).get(BookViewModel::class.java)
    }

    private var _activityBookDetailBinding: ActivityBookDetailBinding? = null
    private val binding get() = _activityBookDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityBookDetailBinding = ActivityBookDetailBinding.inflate(layoutInflater)
        setContentView(_activityBookDetailBinding?.root)
        title = "Detail Buku"

        val id = intent.getIntExtra("book_id",0)

        binding?.btnEdit?.setOnClickListener {
            val intent = Intent(this, EditBookActivity::class.java)
            intent.putExtra("book_id", id)
            startActivity(intent)
        }

        val userId: Int = SessionManager(this).userId

        getBookDetail(id, userId)
    }

    private fun getBookDetail(bookId: Int, userId: Int) {
        viewModel.getDetailBuku(bookId).observe(this, Observer {
            if (it.userId == userId) {
                binding?.btnEdit?.visibility = View.VISIBLE
            }
            binding?.imgBookThumbnail?.let { img ->
                Glide.with(applicationContext)
                    .load(BOOK_THUMBNAIL_BASE_URL + it.userId + "/books/" + it.thumbnail)
                    .into(img)
            }
            binding?.tvBookTitle?.text = it.bookName
            binding?.tvDesciption?.text = it.description
            binding?.tvBookOwner?.text = it.fullName
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
            binding?.bgDim?.visibility = View.INVISIBLE
            binding?.progressBar?.visibility = View.INVISIBLE
        } else {
            binding?.bgDim?.visibility = View.VISIBLE
            binding?.progressBar?.visibility = View.VISIBLE
        }
    }

}