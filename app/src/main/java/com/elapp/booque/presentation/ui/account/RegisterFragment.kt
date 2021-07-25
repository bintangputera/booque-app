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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.elapp.booque.MainActivity
import com.elapp.booque.R
import com.elapp.booque.data.entity.Credential
import com.elapp.booque.data.entity.login.User
import com.elapp.booque.databinding.FragmentRegisterBinding
import com.elapp.booque.presentation.ui.account.handler.RegisterHandler
import com.elapp.booque.presentation.ui.account.handler.RegisterListener
import com.elapp.booque.utils.global.SessionManager
import com.elapp.booque.utils.global.factory.ViewModelFactory
import com.elapp.booque.utils.network.NetworkState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import timber.log.Timber

class RegisterFragment : Fragment(), RegisterListener, RegisterHandler {

    private var _fragmentRegisterBinding: FragmentRegisterBinding? = null
    private val binding get() = _fragmentRegisterBinding

    private lateinit var mGooleSignInClient: GoogleSignInClient

    private val registerViewModel by lazy {
        val factory = ViewModelFactory.getInstance()
        ViewModelProvider(this, factory).get(RegisterViewModel::class.java)
    }

    private val RC_SIGN_UP: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentRegisterBinding = FragmentRegisterBinding.inflate(layoutInflater)
        return _fragmentRegisterBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerViewModel.auth = this
        binding?.handler = this

        initView()
        binding?.txKonfirmLogin?.setOnClickListener {
            it.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun initView() {
        val gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGooleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    private fun signUp() {
        val intent = mGooleSignInClient.signInIntent
        startActivityForResult(intent, RC_SIGN_UP)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_UP) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val acc: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            val email: String = acc?.email.toString()
            val namaLengkap: String = acc?.displayName.toString()
            val action = RegisterFragmentDirections.actionRegisterFragmentToRegisterDataFragment(
                email,
                namaLengkap
            )
            findNavController().navigate(action)
        } catch (e: ApiException) {
            Timber.e("signInResult:failed code= ${e.statusCode}")
        }
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
            "default"
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
        signUp()
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