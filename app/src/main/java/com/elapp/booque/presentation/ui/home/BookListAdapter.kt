package com.elapp.booque.presentation.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.elapp.booque.data.entity.book.Book
import com.elapp.booque.databinding.BookListLayoutBinding
import com.elapp.booque.presentation.ui.book.listener.ItemListener
import com.elapp.booque.utils.global.NetworkAuthConf.BOOK_THUMBNAIL_BASE_URL
import com.squareup.picasso.Picasso

class BookListAdapter: PagingDataAdapter<Book, BookListAdapter.BookViewHolder>(DIFF_CALLBACK) {

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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookListAdapter.BookViewHolder {
        val view = BookListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookListAdapter.BookViewHolder, position: Int) {
        getItem(position)?.let { book ->
            holder.bind(book)
            holder.itemView.setOnClickListener {
                bookItemListener.onItemCLicked(book)
            }
        }

    }

    inner class BookViewHolder(val binding: BookListLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            if (book.status == "2") {
                binding.bgCover.visibility = View.VISIBLE
                binding.tvPesanHabis.visibility = View.VISIBLE
                itemView.isEnabled = false
                itemView.isClickable = false
            } else {
                binding.bgCover.visibility = View.INVISIBLE
                binding.tvPesanHabis.visibility = View.INVISIBLE
                itemView.isEnabled = true
                itemView.isClickable = true
            }
            Picasso.get().load(BOOK_THUMBNAIL_BASE_URL + book.userId + "/books/" + book.thumbnail).into(binding.imgBookThumbnail)
            binding.txBookTitle.text = book.bookName
            binding.txCategoryTitle.text = book.categoryName
            binding.txLocation.text = book.address
        }

    }

}
