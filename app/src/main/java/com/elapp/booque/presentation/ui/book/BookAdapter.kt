package com.elapp.booque.presentation.ui.book

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elapp.booque.data.entity.blog.Blog
import com.elapp.booque.data.entity.book.Book
import com.elapp.booque.databinding.BookListItemBinding
import com.elapp.booque.databinding.BookListLayoutBinding
import com.elapp.booque.presentation.ui.article.listener.BlogListener
import com.squareup.picasso.Picasso

class BookAdapter : PagingDataAdapter<Book, BookAdapter.BookViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Book> = object :
            DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.id == newItem.id && oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding =
            BookListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    inner class BookViewHolder(val binding: BookListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            binding.txBookTitle.text = book.bookName
            binding.txDeskripsi.text = book.description
            binding.txCategory.text = book.categoryName
            Picasso.get().load(book.thumbnail)
        }
    }

}