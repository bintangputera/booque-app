package com.elapp.booque.presentation.ui.city

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.elapp.booque.data.entity.city.City
import com.elapp.booque.databinding.ActivityCityBinding
import com.elapp.booque.presentation.ui.city.adapter.CityAdapter
import com.elapp.booque.presentation.ui.city.listener.CityItemListener
import com.elapp.booque.utils.global.SessionManager
import com.elapp.booque.utils.global.factory.ViewModelFactory

class CityActivity : AppCompatActivity(), CityItemListener {

    private val viewModel by lazy {
        val factory = ViewModelFactory.getInstance()
        ViewModelProvider(this, factory).get(CityViewModel::class.java)
    }

    private var _activityCityBinding: ActivityCityBinding? = null
    private val binding get() = _activityCityBinding

    private lateinit var cityAdapter: CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityCityBinding = ActivityCityBinding.inflate(layoutInflater)
        setContentView(_activityCityBinding?.root)

        title = "Pilih Kota"

        val provinceId = intent.getIntExtra("province_id", 0)

        cityAdapter = CityAdapter()
        binding?.rvCity?.adapter = cityAdapter
        binding?.rvCity?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        cityAdapter.itemClickedListener(this)
        cityAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                binding?.progressBar?.visibility = View.VISIBLE
                binding?.bgDim?.visibility = View.VISIBLE
            } else {
                binding?.progressBar?.visibility = View.GONE
                binding?.bgDim?.visibility = View.GONE

                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                error?.let {
                    Toast.makeText(applicationContext, it.error.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        loadCityListData(provinceId)

    }

    private fun loadCityListData(provinceId: Int) {
        viewModel.getListDataCity(provinceId).observe(this, Observer {
            cityAdapter.submitData(lifecycle, it)
        })
    }

    override fun onItemClicked(city: City) {
        intent.putExtra("city_id", city.id)
        intent.putExtra("city_name", city.cityName)
        SessionManager(this).saveCity(city.cityName)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}