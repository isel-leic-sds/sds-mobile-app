package com.isel.ps.sds.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.isel.ps.sds.R
import com.isel.ps.sds.view.BaseActivity
import com.isel.ps.sds.view.menu.MenuActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<LoginViewModel>() {
    private val loginFactory: LoginFactory = LoginFactory()

    override fun defineViewModel(): Class<LoginViewModel> = LoginViewModel::class.java
    override fun layoutToInflate(): Int = R.layout.activity_login

    override fun doOnCreate(savedInstanceState: Bundle?) {
        Animator { onFinish() }.start()

        viewModel.getLoginState().observe(this, Observer<Boolean> {
            if (it) {
                Toast.makeText(this, "Login successful!", Toast.LENGTH_LONG).show()
                onSuccess()
            } else {
                loginButton.isEnabled = true
            }
        })

        viewModel.getErrorMessage().observe(this, Observer<String> {
            Toast.makeText(this, "Login failed. Try Again!: $it", Toast.LENGTH_LONG).show()
        })

        loginButton.setOnClickListener {
            loginButton.isEnabled = false
            val userName: String = userNameEditText.text.toString()
            val password = passwordEditText.text.toString()

            viewModel.tryLogin(loginFactory.getLogin(userName, password))
        }
    }

    private fun onSuccess() {
        startActivity(Intent(this, MenuActivity::class.java))
        finish()
    }

    private fun isLoggedIn() {
        if (viewModel.isLoggedIn()) {
            onSuccess()
        }
    }

    private fun onFinish() {
        login_title_desc.visibility = View.GONE
        loading_progress_bar.visibility = View.GONE
        login_sds_mini_logo.setImageResource(R.drawable.sds)
        login_sds_mini_logo.animate().apply {
            x(50f)
            y(100f)
            duration = 1000
        }.setListener(SdsAnimatorListener(this))

        isLoggedIn()
    }
}
