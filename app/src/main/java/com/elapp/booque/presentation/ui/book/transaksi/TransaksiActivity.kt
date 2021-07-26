package com.elapp.booque.presentation.ui.book.transaksi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.elapp.booque.databinding.ActivityTransaksiBinding
import com.elapp.booque.presentation.ui.book.BookViewModel
import com.elapp.booque.utils.global.SessionManager
import com.elapp.booque.utils.global.factory.ViewModelFactory

class TransaksiActivity : AppCompatActivity() {

    private val viewModel by lazy {
        val factory = ViewModelFactory.getInstance()
        ViewModelProvider(this, factory).get(BookViewModel::class.java)
    }

    private var _activityTransaksiBinding: ActivityTransaksiBinding? = null
    private val binding get() = _activityTransaksiBinding

    private lateinit var transaksiAdapter: TransaksiAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityTransaksiBinding = ActivityTransaksiBinding.inflate(layoutInflater)
        setContentView(_activityTransaksiBinding?.root)

        title = "Riwayat Transaksi"

        transaksiAdapter = TransaksiAdapter()
        transaksiAdapter.addLoadStateListener { loadState ->
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

        val userId = SessionManager(this).userId

        getListTransaksi(userId)

    }

    private fun getListTransaksi(userId: Int) {
        viewModel.getListTransaksi(userId).observe(this, Observer {
            binding?.rvTransaksi?.adapter = transaksiAdapter
            binding?.rvTransaksi?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            transaksiAdapter.submitData(lifecycle, it)
        })
    }

}