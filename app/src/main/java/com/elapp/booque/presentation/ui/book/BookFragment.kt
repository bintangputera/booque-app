package com.elapp.booque.presentation.ui.book


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.elapp.booque.MainActivity
import com.elapp.booque.databinding.FragmentBookBinding
import com.elapp.booque.utils.network.NetworkState
import timber.log.Timber

class BookFragment : Fragment() {

    private var _fragmentBookBinding: FragmentBookBinding? = null
    private val binding get() = _fragmentBookBinding

    private lateinit var bookAdapter: BookAdapter
    private lateinit var bookViewModel: BookViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentBookBinding = FragmentBookBinding.inflate(layoutInflater)
        return _fragmentBookBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookViewModel = (activity as MainActivity).viewModel

        bookAdapter = BookAdapter()
        with(binding?.rvBook){
            this?.adapter = bookAdapter
            this?.layoutManager = LinearLayoutManager(context?.applicationContext, LinearLayoutManager.HORIZONTAL, false)
        }

        getBookList("")

    }

    private fun getBookList(keyword: String) {
        bookViewModel.getListDataBook(keyword).observe(viewLifecycleOwner, Observer {
            bookAdapter.submitList(it)
        })
        bookViewModel.bookNetworkState.observe(viewLifecycleOwner, Observer {
            Timber.d("Check the network state $it")
            when (it) {
                NetworkState.LOADING -> {
                    Toast.makeText(
                        context?.applicationContext,
                        "Network State Loading",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                NetworkState.LOADED -> {
                    Toast.makeText(
                        context?.applicationContext,
                        "Network State Loaded",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

}