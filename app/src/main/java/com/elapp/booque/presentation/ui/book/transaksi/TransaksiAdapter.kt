package com.elapp.booque.presentation.ui.book.transaksi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.elapp.booque.data.entity.book.Book
import com.elapp.booque.data.entity.transaction.Transaksi
import com.elapp.booque.databinding.TransaksiListBinding

class TransaksiAdapter : PagingDataAdapter<Transaksi, TransaksiAdapter.TransaksiViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Transaksi> = object :
            DiffUtil.ItemCallback<Transaksi>() {
            override fun areItemsTheSame(oldItem: Transaksi, newItem: Transaksi): Boolean {
                return oldItem.bookName == newItem.bookName && oldItem.bookName == newItem.bookName
            }

            override fun areContentsTheSame(oldItem: Transaksi, newItem: Transaksi): Boolean {
                return oldItem.bookName == newItem.bookName
            }

        }
    }

    override fun onBindViewHolder(holder: TransaksiAdapter.TransaksiViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransaksiAdapter.TransaksiViewHolder {
        val view = TransaksiListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransaksiViewHolder(view)
    }

    inner class TransaksiViewHolder(val binding: TransaksiListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(transaksi: Transaksi) {
            binding.txBookTitle.text = transaksi.bookName
            binding.txPenerima.text = "Penerima : ${transaksi.buyer}"
        }
    }

}