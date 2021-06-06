package com.elapp.booque.presentation.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.elapp.booque.MainActivity
import com.elapp.booque.data.entity.Credential
import com.elapp.booque.data.entity.login.User
import com.elapp.booque.databinding.FragmentRegisterDataBinding
import com.elapp.booque.presentation.ui.account.handler.RegisterListener
import com.elapp.booque.utils.global.SessionManager
import com.elapp.booque.utils.network.NetworkState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import timber.log.Timber

class RegisterDataFragment : Fragment(), RegisterListener {

    private var _fragmentRegisterDataBinding: FragmentRegisterDataBinding? = null
    private val binding get() = _fragmentRegisterDataBinding

    private var mGoogleSignInAccount: GoogleSignInClient? = null

    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentRegisterDataBinding = FragmentRegisterDataBinding.inflate(layoutInflater)
        return _fragmentRegisterDataBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInAccount = GoogleSignIn.getClient(requireActivity(), gso)

        registerViewModel = (activity as FormActivity).registerViewModel

        binding?.viewmodel = registerViewModel

        getData()

        binding?.btnBuatAkun?.setOnClickListener {
            registerViewModel.registerRequest("oauth")

            registerViewModel.networkState.observe(viewLifecycleOwner, Observer {
                when (it) {
                    NetworkState.LOADING -> Toast.makeText(context?.applicationContext, "Proses Register", Toast.LENGTH_SHORT).show()

                    NetworkState.LOADED -> Toast.makeText(context?.applicationContext, "Register Berhasil", Toast.LENGTH_SHORT).show()
                }
            })
        }

    }

    private fun getData() {
        val email = arguments?.getString("email")
        val nama = arguments?.getString("nama_lengkap")
        registerViewModel.nama = nama
        registerViewModel.email = email
    }

    private fun signOut() {
        mGoogleSignInAccount?.signOut()
    }

    private fun clearInput(){
        registerViewModel.nama = null
        registerViewModel.email = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        signOut()
        clearInput()
    }

    override fun onSuccess(credential: Credential, response: LiveData<User>) {
        Timber.d("Register success")
        response.observe(this, Observer {
            context?.applicationContext?.let { con ->
                SessionManager(con).saveOAuth(it,credential)
                val intent = Intent(context?.applicationContext, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        })
    }

    override fun onFailure(message: String) {
        Toast.makeText(context?.applicationContext, message, Toast.LENGTH_SHORT).show()
    }

}