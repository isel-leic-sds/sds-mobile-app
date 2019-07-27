package com.isel.ps.sds.view.login

import android.animation.Animator
import android.os.CountDownTimer
import android.view.View
import androidx.core.content.ContextCompat
import com.isel.ps.sds.R
import kotlinx.android.synthetic.main.activity_login.*

class Animator(val loginActivity: LoginActivity) : CountDownTimer(5000, 1000) {
    override fun onFinish() {
        loginActivity.sdsTextView.visibility = View.GONE
        loginActivity.loadingProgressBar.visibility = View.GONE
        loginActivity.rootView.setBackgroundColor(ContextCompat.getColor(loginActivity, R.color.colorSplashText))
        loginActivity.sdsImageView.setImageResource(R.drawable.sds)
        loginActivity.sdsImageView.animate().apply {
            x(50f)
            y(100f)
            duration = 1000
        }.setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                loginActivity.afterAnimationView.visibility = View.VISIBLE
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationStart(p0: Animator?) {

            }
        })

        loginActivity.isLoggedIn()
    }

    override fun onTick(millisUntilFinished: Long) {
        //Do nothing
    }
}