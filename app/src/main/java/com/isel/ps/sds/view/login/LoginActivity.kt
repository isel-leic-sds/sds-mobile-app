package com.isel.ps.sds.view.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.isel.ps.sds.R
import com.isel.ps.sds.view.BaseActivity
import com.isel.ps.sds.view.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<LoginViewModel>() {
    private val loginFactory: LoginFactory = LoginFactory()

    override fun defineViewModel(): Class<LoginViewModel> = LoginViewModel::class.java
    override fun layoutToInflate(): Int = R.layout.activity_login

    override fun doOnCreate(savedInstanceState: Bundle?) {
        viewModel.getLoginState().observe(this, Observer<Boolean> {
            if (it) {
                onSuccess()
            } else {
                loginBtn.isEnabled = true
            }
        })

        viewModel.getErrorMessage().observe(this, Observer<String> {
            Toast.makeText(this, "Login failed. Try Again!: $it", Toast.LENGTH_LONG).show()
        })

        loginBtn.setOnClickListener {
            loginBtn.isEnabled = false
            val userName: String = userName.text.toString()
            val password = password.text.toString()

            viewModel.tryLogin(loginFactory.getLogin(userName, password))
        }
    }

    private fun onSuccess() {
        Toast.makeText(this, "Login successful!", Toast.LENGTH_LONG).show()
        //viewModel.setLoginParameters(this, userInput.text.toString(), orgInput.text.toString(), tokenInput.text.toString())
        startActivity(Intent(this, ProfileActivity::class.java))
    }
}
