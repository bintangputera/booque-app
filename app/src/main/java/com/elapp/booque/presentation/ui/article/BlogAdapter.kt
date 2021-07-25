package com.elapp.booque.presentation.ui.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elapp.booque.data.entity.blog.Blog
import com.elapp.booque.data.entity.book.Book
import com.elapp.booque.databinding.BlogListBinding
import com.elapp.booque.databinding.FragmentBlogBinding
import com.elapp.booque.presentation.ui.article.listener.BlogListener
import com.elapp.booque.utils.global.NetworkAuthConf.THUMBNAIL_BASE_URL
import com.squareup.picasso.Picasso

class BlogAdapter: PagingDataAdapter<Blog, BlogAdapter.BlogViewHolder>(DIFF_CALLBACK) {

    private lateinit var blogListener: BlogListener

    fun itemClicked(listener: BlogListener) {
        this.blogListener = listener
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Blog> = object :
            DiffUtil.ItemCallback<Blog>() {
            override fun areItemsTheSame(oldItem: Blog, newItem: Blog): Boolean {
                return oldItem.id == newItem.id && oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Blog, newItem: Blog): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

    override fun onBindViewHolder(holder: BlogAdapter.BlogViewHolder, position: Int) {
        getItem(position)?.let { blog ->
            holder.bind(blog)
            holder.itemView.setOnClickListener {
                blogListener.onItemClicked(blog)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogAdapter.BlogViewHolder {
        val view = BlogListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return BlogViewHolder(view)
    }

    inner class BlogViewHolder(val binding: BlogListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(blog: Blog) {
            binding.tvBlogTitle.text = blog.articleName
            binding.tvBlogCategory.text = blog.categoryName
            Picasso.get().load(THUMBNAIL_BASE_URL + blog.thumbnail).into(binding.imgBlogThumbnail)
        }
    }

}