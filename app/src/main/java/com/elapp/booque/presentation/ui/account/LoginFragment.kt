
package com.elapp.booque.presentation.ui.account

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.elapp.booque.MainActivity
import com.elapp.booque.R
import com.elapp.booque.data.entity.Credential
import com.elapp.booque.data.entity.login.User
import com.elapp.booque.databinding.FragmentLoginBinding
import com.elapp.booque.presentation.ui.account.handler.AuthListener
import com.elapp.booque.presentation.ui.account.handler.LoginHandler
import com.elapp.booque.utils.global.SessionManager
import com.elapp.booque.utils.network.NetworkState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginFragment : Fragment(), LoginHandler, AuthListener {

    private var _fragmentLoginBinding: FragmentLoginBinding? = null
    private val binding get() = _fragmentLoginBinding

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private lateinit var formViewModel: FormViewModel

    private val RC_SIGN_IN = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentLoginBinding = FragmentLoginBinding.inflate(inflater)
        return _fragmentLoginBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        formViewModel = (activity as FormActivity).viewModel

        binding?.txKonfirmRegister?.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding?.handler = this
        binding?.viewmodel = formViewModel
        formViewModel.auth = this

        initView()

        mGoogleSignInClient.signOut()
    }

    private fun initView() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        }

    private fun signInGoogle() {
        val intent = mGoogleSignInClient.signInIntent
        startActivityForResult(intent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val acc: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            val email: String = acc?.email.toString()
            formViewModel.oauthLogin(email)
            formViewModel.loginNetworkState.observe(viewLifecycleOwner, Observer {
                when (it) {
                    NetworkState.LOADING -> {
                        isLoading(true)
                    }
                    NetworkState.LOADED -> {
                        isLoading(false)
                    }
                    NetworkState.UNKNOWN -> {
                        Toast.makeText(
                            context?.applicationContext,
                            "Terdapat kesalahan coba lagi",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
        } catch (e: ApiException) {
            Timber.e("signInResult:failed code=%s", e.statusCode)
        }

    }

    override fun onLoginClicked(view: View) {
        formViewModel.loginRequest()
        formViewModel.loginNetworkState.observe(viewLifecycleOwner, Observer {
            when (it) {
                NetworkState.LOADING -> {
                    isLoading(true)
                }
                NetworkState.LOADED -> {
                    isLoading(false)
                }
                else -> {
                    Toast.makeText(
                        context?.applicationContext,
                        it.status.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    override fun onGoogleButtonClicked(view: View) {
        Timber.d("clicked")
        signInGoogle()
    }

    override fun onAuthenticating() {
        Timber.d("Login authenticating")
    }

    override fun onSuccess(credential: Credential, response: LiveData<User>) {
        Timber.d("Login success")
        response.observe(this, Observer {
            context?.applicationContext?.let { con ->
                SessionManager(con).saveOAuth(it, credential)
                val intent = Intent(context?.applicationContext, MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
                Timber.d("Activity finished")
            }
        })
    }

    override fun onFailure(message: String) {
        Toast.makeText(context?.applicationContext, message, Toast.LENGTH_SHORT).show()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentLoginBinding = null
    }

    override fun onResume() {
        super.onResume()
        mGoogleSignInClient.signOut()
    }

}