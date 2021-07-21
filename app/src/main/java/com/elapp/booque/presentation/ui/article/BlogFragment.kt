package com.elapp.booque.presentation.ui.article

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.elapp.booque.R
import com.elapp.booque.data.entity.blog.Blog
import com.elapp.booque.databinding.FragmentBlogBinding
import com.elapp.booque.presentation.ui.article.listener.BlogListener
import com.elapp.booque.utils.global.NetworkAuthConf.BLOG_URL
import com.elapp.booque.utils.global.factory.ViewModelFactory

class BlogFragment : Fragment(), BlogListener {

    private val viewModel by lazy {
        val factory = ViewModelFactory.getInstance()
        ViewModelProvider(this, factory).get(BlogViewModel::class.java)
    }

    private var _fragmentBlogBinding: FragmentBlogBinding? = null
    private val binding get() = _fragmentBlogBinding

    private lateinit var blogAdapter: BlogAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentBlogBinding = FragmentBlogBinding.inflate(inflater, container, false)
        return _fragmentBlogBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        blogAdapter = BlogAdapter()
        blogAdapter.itemClicked(this)
        blogAdapter.addLoadStateListener { loadState ->
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
                    Toast.makeText(context?.applicationContext, it.error.message , Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding?.rvBlog?.adapter = blogAdapter
        binding?.rvBlog?.layoutManager =
            LinearLayoutManager(context?.applicationContext, LinearLayoutManager.VERTICAL, false)

        getListBlog()

    }

    private fun getListBlog() {
        viewModel.getListDataBlog().observe(viewLifecycleOwner, Observer {
            blogAdapter.submitData(lifecycle, it)
        })
    }

    override fun onItemClicked(blog: Blog) {
        val intent = Intent(context?.applicationContext, ArticleWebViewActivity::class.java)
        val blogUrl = "$BLOG_URL${blog.categoryName}/${blog.slug}"
        intent.putExtra("selectedUrl", blogUrl)
        startActivity(intent)
    }

}