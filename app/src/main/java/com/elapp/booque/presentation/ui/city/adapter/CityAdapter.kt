package com.elapp.booque.presentation.ui.city.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.elapp.booque.data.entity.city.City
import com.elapp.booque.databinding.ProvinceListBinding
import com.elapp.booque.presentation.ui.city.listener.CityItemListener

class CityAdapter : PagingDataAdapter<City, CityAdapter.CityViewHolder>(DIFF_CALLBACK) {

    private lateinit var cityItemListener: CityItemListener

    fun itemClickedListener(listener: CityItemListener) {
        this.cityItemListener = listener
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<City> = object :
            DiffUtil.ItemCallback<City>() {
            override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
                return oldItem.id == newItem.id && oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityAdapter.CityViewHolder {
        val provinceListBinding =
            ProvinceListBinding.inflate(
                LayoutInflater.from(
                    parent.context.applicationContext
                ),
                parent, false
            )
        return CityViewHolder(provinceListBinding)
    }

    override fun onBindViewHolder(holder: CityAdapter.CityViewHolder, position: Int) {
        getItem(position)?.let { city ->
            holder.bind(city)
            holder.itemView.setOnClickListener {
                cityItemListener.onItemClicked(city)
            }
        }
    }

    inner class CityViewHolder(val binding: ProvinceListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(city: City) {
            binding.txProvince.text = city.cityName
        }
    }
}