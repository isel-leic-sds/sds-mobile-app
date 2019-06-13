package com.isel.ps.sds.view.login

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.View.VISIBLE
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

        object : CountDownTimer(5000, 1000) {
            override fun onFinish() {
                sdsTextView.visibility = View.GONE
                loadingProgressBar.visibility = View.GONE
                rootView.setBackgroundColor(ContextCompat.getColor(this@LoginActivity, R.color.colorSplashText))
                sdsImageView.setImageResource(R.drawable.sds)
                startAnimation()
            }

            override fun onTick(p0: Long) {}
        }.start()
    }

    private fun startAnimation() {
        sdsImageView.animate().apply {
            x(50f)
            y(100f)
            duration = 1000
        }.setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                afterAnimationView.visibility = VISIBLE
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationStart(p0: Animator?) {

            }
        })

        viewModel.getLoginState().observe(this, Observer<Boolean> {
            if (it) {
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
        Toast.makeText(this, "Login successful!", Toast.LENGTH_LONG).show()
        startActivity(Intent(this, MenuActivity::class.java))
    }
}

