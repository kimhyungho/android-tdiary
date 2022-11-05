package com.hardy.yongbyung.ui.login

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.GoogleAuthProvider
import com.hardy.domain.model.User
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.FragmentLoginBinding
import com.hardy.yongbyung.ui.base.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseViewModelFragment<FragmentLoginBinding, LoginViewModel>(
    R.layout.fragment_login
) {
    private lateinit var getResult: ActivityResultLauncher<IntentSenderRequest>

    override val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getResult =
            registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    try {
                        val credentials =
                            viewModel.oneTapClient.getSignInCredentialFromIntent(result.data)
                        val googleIdToken = credentials.googleIdToken
                        val googleCredentials =
                            GoogleAuthProvider.getCredential(googleIdToken, null)
                        viewModel.signInWithGoogle(googleCredentials)
                    } catch (_: Exception) {
                    }
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            lifecycleScope.launchWhenStarted {
                oneTapSignInResult.collect { result ->
                    result?.let {
                        val intent =
                            IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                        getResult.launch(intent)
                    }
                }
            }

            lifecycleScope.launchWhenStarted {
                user.collect { user ->
                    user?.let {
                        if (user is User.UnRegistered) {
                            navController.navigate(LoginFragmentDirections.actionDestLoginToDestAgreement())
                        }
                    }
                }
            }

            lifecycleScope.launchWhenStarted {
                error.collect {
                    Snackbar.make(viewDataBinding.rootLayout, it, Snackbar.LENGTH_SHORT).show()
                }
            }
        }


        with(viewDataBinding) {
            googleLoginButton.setOnClickListener {
                viewModel?.oneTapSignIn()
            }
        }
    }
}