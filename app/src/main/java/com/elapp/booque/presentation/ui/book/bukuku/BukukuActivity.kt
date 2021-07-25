package com.elapp.booque.presentation.ui.book.bukuku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.elapp.booque.data.entity.book.Book
import com.elapp.booque.databinding.ActivityBukukuBinding
import com.elapp.booque.presentation.ui.book.BookAdapter
import com.elapp.booque.presentation.ui.book.BookViewModel
import com.elapp.booque.presentation.ui.book.detail.BookDetailActivity
import com.elapp.booque.presentation.ui.book.listener.ItemListener
import com.elapp.booque.utils.global.SessionManager
import com.elapp.booque.utils.global.factory.ViewModelFactory

class BukukuActivity : AppCompatActivity(), ItemListener {

    private val viewModel by lazy {
        val factory = ViewModelFactory.getInstance()
        ViewModelProvider(this, factory).get(BookViewModel::class.java)
    }

    private var _activityBukukuBinding: ActivityBukukuBinding? = null
    private val binding get() = _activityBukukuBinding

    private lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityBukukuBinding = ActivityBukukuBinding.inflate(layoutInflater)
        setContentView(_activityBukukuBinding?.root)
        title = "Daftar Buku Saya"

        bookAdapter = BookAdapter()
        bookAdapter.onItemClicked(this)
        binding?.rvBook?.adapter = bookAdapter
        binding?.rvBook?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        bookAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                binding?.progressBar?.visibility = View.VISIBLE
                binding?.bgDim?.visibility = View.VISIBLE
            }else {
                binding?.progressBar?.visibility = View.GONE
                binding?.bgDim?.visibility = View.GONE

                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                error?.let {
                    Toast.makeText(applicationContext, it.error.message , Toast.LENGTH_SHORT).show()
                }
            }
        }

        getDataBukuku(SessionManager(this).userId)

    }

    private fun getDataBukuku(userId: Int) {
        viewModel.getListDataBukuku(userId).observe(this, Observer {
            bookAdapter.submitData(lifecycle, it)
        })
    }

    override fun onItemCLicked(book: Book) {
        val intent = Intent(this, BookDetailActivity::class.java)
        intent.putExtra("book_id", book.id)
        startActivity(intent)
    }

}