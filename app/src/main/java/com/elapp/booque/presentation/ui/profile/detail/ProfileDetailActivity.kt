package com.elapp.booque.presentation.ui.profile.detail

import android.app.Activity
import android.content.Intent
import android.icu.number.IntegerWidth
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elapp.booque.data.entity.province.Province
import com.elapp.booque.databinding.ActivityProfileDetailBinding
import com.elapp.booque.presentation.ui.city.CityActivity
import com.elapp.booque.presentation.ui.profile.ProfileViewModel
import com.elapp.booque.presentation.ui.province.ProvinceActivity
import com.elapp.booque.utils.global.SessionManager
import com.elapp.booque.utils.global.factory.ViewModelFactory
import com.elapp.booque.utils.network.NetworkState

class ProfileDetailActivity : AppCompatActivity() {

    private val viewModel by lazy {
        val factory = ViewModelFactory.getInstance()
        ViewModelProvider(this, factory).get(ProfileViewModel::class.java)
    }

    private var provinceId: Int = 0
    private var cityId: Int = 0

    private lateinit var provinceName: String
    private lateinit var cityName: String

    private var _profileDetailActivityBinding: ActivityProfileDetailBinding? = null
    private val binding get() = _profileDetailActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _profileDetailActivityBinding = ActivityProfileDetailBinding.inflate(layoutInflater)
        setContentView(_profileDetailActivityBinding?.root)

        title = "Detail Profile"

        val userId = SessionManager(this).userId

        getProfileDetail(userId)

        binding?.btnSearchProvince?.setOnClickListener {
            provinceLauncher.launch(Intent(this, ProvinceActivity::class.java))
        }

        binding?.btnSearchCity?.setOnClickListener {
            val intent = Intent(this, CityActivity::class.java)
            intent.putExtra("province_id", provinceId)
            cityLauncer.launch(intent)
        }

        binding?.btnSimpanPerubahan?.setOnClickListener {

            val phone = Integer.parseInt(binding?.edtNoTelpon?.text.toString())

            updateProfile(
                userId,
                binding?.edtName?.text?.toString(),
                binding?.edtAlamat?.text?.toString(),
                phone,
                cityId,
                provinceId
            )
        }
    }

    private val provinceLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            provinceId = intent?.getIntExtra("province_id", 0)!!
            Toast.makeText(this, "Province id : $provinceId", Toast.LENGTH_SHORT).show()
            provinceName = SessionManager(this).provinceName.toString()
            binding?.edtProvinsi?.setText(provinceName)
            Toast.makeText(this, "Id terparsed : $provinceId dan Nama $provinceName", Toast.LENGTH_SHORT).show()
        }
    }

    private val cityLauncer = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            cityId = intent?.getIntExtra("city_id", 0)!!
            Toast.makeText(this, "City Id : $cityId", Toast.LENGTH_SHORT).show()
            cityName = SessionManager(this).cityName.toString()
            binding?.edtKota?.setText(cityName)
        }
    }

    private fun getProfileDetail(userId: Int) {
        viewModel.getProfileDetail(userId).observe(this, Observer {
            binding?.edtName?.setText(it.fullName)
            it.address.let { alamat ->
                binding?.edtAlamat?.setText(alamat)
            }
            it.phone.let { phone ->
                binding?.edtNoTelpon?.setText(phone)
            }
        })
        viewModel.networkState.observe(this, Observer {
            when (it) {

                NetworkState.LOADING -> isLoading(true)

                NetworkState.LOADED -> isLoading(false)
            }
        })
    }

    private fun updateProfile(
        userId: Int,
        fullName: String?,
        address: String?,
        phone: Int?,
        cityId: Int?,
        provinceId: Int?
    ) {
        viewModel.updateProfile(
            userId,
            fullName,
            address,
            phone,
            cityId,
            provinceId
        ).observe(this, Observer { response ->
            cityId?.toInt()?.let { cityId ->
                provinceId?.toInt()?.let { provinceId ->
                    SessionManager(this).saveUpdate(
                        userId,
                        fullName.toString(),
                        address.toString(),
                        phone.toString(),
                        cityId,
                        provinceId
                    )
                }
            }
            Toast.makeText(this, response.debugMsg, Toast.LENGTH_SHORT).show()
            if (response.debugMsg == "Update Success") {
                finish()
            }
        })
        viewModel.networkState.observe(this, Observer {
            when(it) {
                NetworkState.LOADING -> isLoading(true)
                NetworkState.LOADED -> {
                    isLoading(false)
                }
            }
        })
    }

    private fun isLoading(status: Boolean) {
        if (!status) {
            binding?.bgDim?.visibility = View.INVISIBLE
            binding?.progressBar?.visibility = View.INVISIBLE
            binding?.edtProvinsi?.setText(SessionManager(this).provinceName)
            binding?.edtKota?.setText(SessionManager(this).cityName)
        } else {
            binding?.bgDim?.visibility = View.VISIBLE
            binding?.progressBar?.visibility = View.VISIBLE
        }
    }

}