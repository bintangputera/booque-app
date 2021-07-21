package com.elapp.booque.presentation.ui.province

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.elapp.booque.R
import com.elapp.booque.data.entity.province.Province
import com.elapp.booque.databinding.ActivityProvinceBinding
import com.elapp.booque.presentation.ui.province.adapter.ProvinceAdapter
import com.elapp.booque.presentation.ui.province.listener.ItemListener
import com.elapp.booque.utils.global.factory.ViewModelFactory

class ProvinceActivity : AppCompatActivity(), ItemListener {

    private val viewModel by lazy {
        val factory = ViewModelFactory.getInstance()
        ViewModelProvider(this, factory).get(ProvinceViewModel::class.java)
    }

    private var _activityProvinceBinding: ActivityProvinceBinding? = null
    private val binding get() = _activityProvinceBinding

    private lateinit var provinceAdapter: ProvinceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityProvinceBinding = ActivityProvinceBinding.inflate(layoutInflater)
        setContentView(_activityProvinceBinding?.root)

        title = "Pilih Provinsi"

        binding?.rvProvince?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        provinceAdapter = ProvinceAdapter()
        provinceAdapter.itemOnClickListener(this)
        provinceAdapter.addLoadStateListener { loadState ->
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

        getProvinceList()
    }

    private fun getProvinceList() {
        viewModel.getListDataProvince().observe(this, Observer {
            provinceAdapter.submitData(lifecycle, it)
            binding?.rvProvince?.adapter = provinceAdapter
        })
    }

    override fun onClicked(province: Province) {
        Toast.makeText(this, "Cek : ${province.id}", Toast.LENGTH_SHORT).show()
    }

}