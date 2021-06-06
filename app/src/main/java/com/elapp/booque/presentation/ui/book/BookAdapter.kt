package com.elapp.booque.presentation.ui.book

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elapp.booque.data.entity.book.Book
import com.elapp.booque.databinding.BookListLayoutBinding
import com.squareup.picasso.Picasso

class BookAdapter: PagedListAdapter<Book, BookAdapter.BookViewHolder>(DIFF_CALLBACK) {

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

    override fun onBindViewHolder(holder: BookAdapter.BookViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookAdapter.BookViewHolder {
        val bookListLayoutBinding = BookListLayoutBinding.inflate(
            LayoutInflater.from(parent.context.applicationContext),
            parent,
            false)
        return BookViewHolder(bookListLayoutBinding)
    }

    inner class BookViewHolder(val binding: BookListLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(book: Book){
            binding.txLocation.text = book.address
            binding.txCategoryTitle.text = book.categoryName
            binding.txBookTitle.text = book.bookName
            Picasso.get().load(book.thumbnail)
        }
    }

}