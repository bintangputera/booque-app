package com.elapp.booque.presentation.ui.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.elapp.booque.R
import com.elapp.booque.data.entity.book.Book
import com.elapp.booque.databinding.FragmentHomeBinding
import com.elapp.booque.presentation.ui.article.ArticleWebViewActivity
import com.elapp.booque.presentation.ui.book.BookViewModel
import com.elapp.booque.presentation.ui.book.detail.BookDetailActivity
import com.elapp.booque.presentation.ui.book.listener.ItemListener
import com.elapp.booque.utils.SharedPreferencesKey.KEY_FULL_NAME
import com.elapp.booque.utils.SharedPreferencesKey.USER_PREFS_NAME
import com.elapp.booque.utils.global.factory.ViewModelFactory
import com.elapp.booque.utils.helper.ArticleOnClickListener
import timber.log.Timber


class HomeFragment : Fragment(), ArticleOnClickListener, ItemListener {

    private val viewModel by lazy {
        val factory = ViewModelFactory.getInstance()
        ViewModelProvider(this, factory).get(BookViewModel::class.java)
    }

    private var _fragmentHomeBinding: FragmentHomeBinding? = null
    private val binding get() = _fragmentHomeBinding

    private lateinit var sharedPrefences: SharedPreferences

    private lateinit var bannerAdapter: BannerAdapter

    private lateinit var bookListAdapter: BookListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentHomeBinding = FragmentHomeBinding.inflate(inflater,container, false )
        return _fragmentHomeBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPrefences = context?.getSharedPreferences(USER_PREFS_NAME, Context.MODE_PRIVATE)!!
        loadDataFromPreferences()

        bookListAdapter = BookListAdapter()
        bookListAdapter.onItemClicked(this)

        binding?.rvBook?.adapter = bookListAdapter
        binding?.rvBook?.layoutManager = LinearLayoutManager(context?.applicationContext, LinearLayoutManager.HORIZONTAL,false)

        binding?.txLihatSelengkapnya?.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_bookFragment)
        }

        initBanner()

        getBookList()

    }

    private fun getBookList() {
        viewModel.getListDataBook().observe(viewLifecycleOwner, Observer {
            bookListAdapter.submitData(lifecycle, it)
        })
    }

    private fun loadDataFromPreferences() {
        val namaLengkap = sharedPrefences.getString(KEY_FULL_NAME, null)
        binding?.txHaiNama?.text = context?.applicationContext?.getString(R.string.welcome_title, namaLengkap)
        Timber.d("get name from shared preferences $namaLengkap")
    }

    private fun initBanner() {
        val banner = listOf(
            BannerModel(
                name = "Puncak badai uang",
                image = "https://s2.bukalapak.com/uploads/promo_partnerinfo_bloggy/2842/Bloggy_1_puncak.jpg"
            ),
            BannerModel(
                name = "Puncak badai uang",
                image = "https://s2.bukalapak.com/uploads/promo_partnerinfo_bloggy/2842/Bloggy_1_puncak.jpg"
            ),
            BannerModel(
                name = "Puncak badai uang",
                image = "https://s2.bukalapak.com/uploads/promo_partnerinfo_bloggy/2842/Bloggy_1_puncak.jpg"
            ),
            BannerModel(
                name = "Puncak badai uang",
                image = "https://s2.bukalapak.com/uploads/promo_partnerinfo_bloggy/2842/Bloggy_1_puncak.jpg"
            ),
            BannerModel(
                name = "Puncak badai uang",
                image = "https://s2.bukalapak.com/uploads/promo_partnerinfo_bloggy/2842/Bloggy_1_puncak.jpg"
            )
        )

        bannerAdapter = BannerAdapter(context?.applicationContext, banner)
        binding?.viewPagerBanner?.adapter = bannerAdapter
        bannerAdapter.itemOnClickListener(this)
        binding?.indicator?.setViewPager(binding?.viewPagerBanner)
    }

    override fun onItemClicked(url: String) {
        val intent = Intent(context, ArticleWebViewActivity::class.java)
        intent.putExtra("selectedUrl", url)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentHomeBinding = null
    }

    override fun onItemCLicked(book: Book) {
        val intent = Intent(context?.applicationContext, BookDetailActivity::class.java)
        intent.putExtra("book_id", book.id)
        startActivity(intent)
    }

}