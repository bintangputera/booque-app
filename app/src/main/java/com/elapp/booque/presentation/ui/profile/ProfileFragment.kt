package com.elapp.booque.presentation.ui.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.elapp.booque.R
import com.elapp.booque.databinding.FragmentProfileBinding
import com.elapp.booque.presentation.ui.account.FormActivity
import com.elapp.booque.presentation.ui.profile.detail.ProfileDetailActivity
import com.elapp.booque.utils.SharedPreferencesKey.USER_PREFS_NAME

class ProfileFragment : Fragment() {

    private var _fragmentProfileBinding: FragmentProfileBinding? = null
    private val binding get() = _fragmentProfileBinding

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)
        return _fragmentProfileBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = activity?.getSharedPreferences(USER_PREFS_NAME, Context.MODE_PRIVATE)!!

        binding?.btnLogout?.setOnClickListener {
            openLogoutDialog()
        }

        binding?.layoutAkunSaya?.setOnClickListener {
            startActivity(Intent(context?.applicationContext, ProfileDetailActivity::class.java))
        }
    }

    private fun openLogoutDialog() {
        val alertDialog = this.context?.let { AlertDialog.Builder(it) }
        alertDialog?.setTitle("Logout?")
            ?.setPositiveButton(
                "Logout"
            ) { _, _ -> logout() }
            ?.setNegativeButton("Cancel", null)
        val alert = alertDialog?.create()
        alert?.show()
    }

    private fun logout() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear().apply()
        val intent = Intent(this.context, FormActivity::class.java)
        activity?.finish()
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentProfileBinding = null
    }

}