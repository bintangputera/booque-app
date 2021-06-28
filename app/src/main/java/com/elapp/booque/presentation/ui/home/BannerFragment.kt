package com.elapp.booque.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elapp.booque.R
import com.elapp.booque.databinding.FragmentBannerBinding
import com.squareup.picasso.Picasso

class BannerFragment : Fragment() {

    private var _bannerFragmentBinding: FragmentBannerBinding? = null
    private val binding get() = _bannerFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bannerFragmentBinding = FragmentBannerBinding.inflate(layoutInflater)
        return _bannerFragmentBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = arguments?.getString("img")

        url.let {
            Picasso.get().load(url).into(binding?.imgBanner)
        }
    }

}