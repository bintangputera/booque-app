package com.elapp.booque.presentation.ui.profile.detail

import android.content.Intent
import android.icu.number.IntegerWidth
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
            startActivity(Intent(this, ProvinceActivity::class.java))
        }

        binding?.btnSearchCity?.setOnClickListener {
            startActivity(Intent(this, CityActivity::class.java))
        }

        binding?.btnSimpanPerubahan?.setOnClickListener {

            val phone = Integer.parseInt(binding?.edtNoTelpon?.text.toString())

            updateProfile(
                userId,
                binding?.edtName?.text?.toString(),
                binding?.edtAlamat?.text?.toString(),
                phone,
                1326,
                1
            )
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
        } else {
            binding?.bgDim?.visibility = View.VISIBLE
            binding?.progressBar?.visibility = View.VISIBLE
        }
    }

}