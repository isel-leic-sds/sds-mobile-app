package com.isel.ps.sds.view.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
        Animator(this).start()

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

    fun isLoggedIn() {
        if (viewModel.isLoggedIn()) {
            onSuccess()
        }
    }
}

