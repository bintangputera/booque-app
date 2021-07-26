package com.elapp.booque.presentation.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.elapp.booque.databinding.FragmentRegisterDataBinding
import com.elapp.booque.presentation.ui.account.handler.RegisterHandler
import com.elapp.booque.presentation.ui.account.handler.RegisterListener
import com.elapp.booque.utils.global.factory.ViewModelFactory
import com.elapp.booque.utils.network.NetworkState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class RegisterDataFragment : Fragment(), RegisterListener, RegisterHandler {

    private var _fragmentRegisterDataBinding: FragmentRegisterDataBinding? = null
    private val binding get() = _fragmentRegisterDataBinding

    private var mGoogleSignInAccount: GoogleSignInClient? = null

    private val registerViewModel by lazy {
        val factory = ViewModelFactory.getInstance()
        ViewModelProvider(this, factory).get(RegisterViewModel::class.java)
    }

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

        binding?.handler = this
        registerViewModel.auth = this

        getData()

    }

    private fun getData() {
        val email = arguments?.getString("email")
        val nama = arguments?.getString("nama_lengkap")
        binding?.edtEmail?.setText(email.toString())
        binding?.edtNama?.setText(nama.toString())
    }

    private fun signOut() {
        mGoogleSignInAccount?.signOut()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        signOut()
    }

    override fun onSuccess(message: String) {
        Toast.makeText(context?.applicationContext, message, Toast.LENGTH_SHORT).show()
    }


    override fun onFailure(message: String) {
        Toast.makeText(context?.applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onRegisterClicked(view: View) {
        registerViewModel.registerRequest(
            binding?.edtEmail?.text.toString(),
            binding?.edtPassword?.text.toString(),
            binding?.edtNama?.text.toString(),
            "oauth"
        )
        registerViewModel.networkState.observe(viewLifecycleOwner, Observer {
            when (it) {
                NetworkState.LOADED -> {
                    isLoading(false)
                    view.findNavController().popBackStack()
                }
                NetworkState.LOADING -> {
                    isLoading(true)
                }
            }
        })
    }

    override fun onGoogleButtonClicked(view: View) {
        /* Nothing to do */
    }

    private fun isLoading(status: Boolean) {
        if (!status) {
            binding?.progressLogin?.visibility = View.GONE
            binding?.backgroundDim?.visibility = View.GONE
        } else {
            binding?.progressLogin?.visibility = View.VISIBLE
            binding?.backgroundDim?.visibility = View.VISIBLE
        }
    }

}