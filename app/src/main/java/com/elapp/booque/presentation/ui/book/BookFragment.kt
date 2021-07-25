package com.elapp.booque.presentation.ui.book


import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.elapp.booque.MainActivity
import com.elapp.booque.data.entity.book.Book
import com.elapp.booque.databinding.FragmentBookBinding
import com.elapp.booque.presentation.ui.book.detail.BookDetailActivity
import com.elapp.booque.presentation.ui.book.listener.ItemListener
import com.elapp.booque.utils.global.factory.ViewModelFactory
import com.elapp.booque.utils.network.NetworkState
import kotlinx.android.synthetic.main.fragment_book.*
import timber.log.Timber

class BookFragment : Fragment(), ItemListener {

    private var _fragmentBookBinding: FragmentBookBinding? = null
    private val binding get() = _fragmentBookBinding

    private lateinit var bookAdapter: BookAdapter

    private val bookViewModel by lazy {
        val factory = ViewModelFactory.getInstance()
        ViewModelProvider(this, factory).get(BookViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentBookBinding = FragmentBookBinding.inflate(layoutInflater)
        return _fragmentBookBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookAdapter = BookAdapter()
        bookAdapter.onItemClicked(this)
        bookAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                binding?.progressLogin?.visibility = View.VISIBLE
                binding?.backgroundDim?.visibility = View.VISIBLE
            }else {
                binding?.progressLogin?.visibility = View.GONE
                binding?.backgroundDim?.visibility = View.GONE

                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                error?.let {
                    Toast.makeText(context?.applicationContext, it.error.message , Toast.LENGTH_SHORT).show()
                }
            }
        }

        with(binding?.rvBook){
            this?.adapter = bookAdapter
            this?.layoutManager = LinearLayoutManager(context?.applicationContext, LinearLayoutManager.VERTICAL, false)
        }

        getBookList()

    }

    private fun getBookList() {
        bookViewModel.getListDataBook().observe(viewLifecycleOwner, Observer {
            bookAdapter.submitData(lifecycle, it)
        })
    }

    override fun onItemCLicked(book: Book) {
        val intent = Intent(context?.applicationContext, BookDetailActivity::class.java)
        intent.putExtra("book_id", book.id)
        startActivity(intent)
    }

}