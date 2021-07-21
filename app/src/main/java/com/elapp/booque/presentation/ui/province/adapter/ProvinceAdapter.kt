package com.elapp.booque.presentation.ui.province.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.elapp.booque.data.entity.province.Province
import com.elapp.booque.databinding.ProvinceListBinding
import com.elapp.booque.presentation.ui.province.listener.ItemListener

class ProvinceAdapter :
    PagingDataAdapter<Province, ProvinceAdapter.ProvinceViewHolder>(DIFF_CALLBACK) {

    private lateinit var itemListener: ItemListener

    fun itemOnClickListener(listener: ItemListener) {
        this.itemListener = listener
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Province> = object :
            DiffUtil.ItemCallback<Province>() {
            override fun areItemsTheSame(oldItem: Province, newItem: Province): Boolean {
                return oldItem.id == newItem.id && oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Province, newItem: Province): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

    override fun onBindViewHolder(holder: ProvinceAdapter.ProvinceViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
        holder.itemView.setOnClickListener {
            getItem(position)?.let {
                itemListener.onClicked(it)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProvinceAdapter.ProvinceViewHolder {
        val provinceListBinding =
            ProvinceListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProvinceViewHolder(provinceListBinding)
    }

    inner class ProvinceViewHolder(val binding: ProvinceListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(province: Province) {
            binding.txProvince.text = province.provinceName
        }
    }

}