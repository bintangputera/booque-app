package com.elapp.booque.presentation.ui.city.listener

import com.elapp.booque.data.entity.city.City

interface CityItemListener {
    fun onItemClicked(city: City)
}