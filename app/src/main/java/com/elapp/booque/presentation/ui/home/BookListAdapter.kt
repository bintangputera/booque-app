package com.elapp.booque.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elapp.booque.data.entity.book.Book
import com.elapp.booque.databinding.BookListLayoutBinding
import com.squareup.picasso.Picasso

class BookListAdapter(private var mList: List<Book>): RecyclerView.Adapter<BookListAdapter.BookViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookListAdapter.BookViewHolder {
        val view = BookListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(view)
    }

    override fun getItemCount(): Int = 5

    override fun onBindViewHolder(holder: BookListAdapter.BookViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    inner class BookViewHolder(val binding: BookListLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            Picasso.get().load(book.thumbnail).into(binding.imgBookThumbnail)
            binding.txBookTitle.text = book.bookName
            binding.txCategoryTitle.text = book.categoryName
            binding.txLocation.text = book.address
        }

    }

}
