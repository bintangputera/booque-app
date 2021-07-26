package com.elapp.booque.presentation.ui.book

import android.view.LayoutInflater
import android.view.View
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
import com.elapp.booque.presentation.ui.book.listener.ItemListener
import com.elapp.booque.utils.global.NetworkAuthConf
import com.squareup.picasso.Picasso

class BookAdapter : PagingDataAdapter<Book, BookAdapter.BookViewHolder>(DIFF_CALLBACK) {

    private lateinit var bookItemListener: ItemListener

    fun onItemClicked(listener: ItemListener) {
        this.bookItemListener = listener
    }

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
        getItem(position)?.let { book ->
            holder.bind(book)
            holder.itemView.setOnClickListener {
                bookItemListener.onItemCLicked(book)
            }
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
            if (book.status == "2") {
                binding.bgCover.visibility = View.VISIBLE
                binding.tvPesanHabis.visibility = View.VISIBLE
            }
            binding.txBookTitle.text = book.bookName
            binding.txCategory.text = book.categoryName
            Picasso.get().load(NetworkAuthConf.BOOK_THUMBNAIL_BASE_URL + book.userId + "/books/" + book.thumbnail).into(binding.imgBookThumbnail)
        }
    }

}