package com.isel.ps.sds.view.login

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import com.example.myapplication.LoginViewModel
import com.isel.ps.sds.R
import com.isel.ps.sds.view.BaseActivity
import com.isel.ps.sds.view.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_login.*
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class LoginActivity : BaseActivity<LoginViewModel>() {

    override fun defineViewModel(): Class<LoginViewModel> = LoginViewModel::class.java
    override fun layoutToInflate(): Int = R.layout.activity_login

    override fun doOnCreate(savedInstanceState: Bundle?) {

        object : CountDownTimer(5000, 1000) {
            override fun onFinish() {
                sdsTextView.visibility = View.GONE
                loadingProgressBar.visibility = View.GONE
                rootView.setBackgroundColor(ContextCompat.getColor(this@LoginActivity, R.color.colorSplashText))
                sdsImageView.setImageResource(R.drawable.sds)//background_color_book)
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


        loginButton.setOnClickListener {
            var userName: String = userNameEditText.text.toString()
            var password = passwordEditText.text.toString()
            var userID = userIDEditText.text.toString() // encrypt(password.text.toString())

            viewModel.init(userName, password, userID)
            val i: Intent = Intent(this, ProfileActivity::class.java)
            startActivity(i)
        }

    }


    fun encrypt(pw: String): String {
        val ENCRYPTION_KEY = "POLICARPO É GAY!"
        val input = pw.toString().toByteArray((charset("utf-8")))
        val md = MessageDigest.getInstance("MD5")
        val thedigest = md.digest(ENCRYPTION_KEY.toByteArray((charset("utf-8"))))
        val skc = SecretKeySpec(thedigest, "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, skc)

        val cipherText = ByteArray(cipher.getOutputSize(input.size))
        var ctLength = cipher.update(input, 0, input.size, cipherText, 0)
        ctLength += cipher.doFinal(cipherText, ctLength)

        val query = Base64.encodeToString(cipherText, Base64.DEFAULT)
        Log.i("LoginActivity", query)
        return query
    }
}

