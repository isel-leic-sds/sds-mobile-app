package com.isel.ps.sds.view.login

import android.os.CountDownTimer
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*

class Animator(private val animatorOnFinish: () -> Unit) : CountDownTimer(5000, 1000) {
    override fun onFinish() {
        animatorOnFinish()
    }

    override fun onTick(millisUntilFinished: Long) {
        //Do nothing
    }
}

class SdsAnimatorListener(private val activity: LoginActivity) : android.animation.Animator.AnimatorListener {
    override fun onAnimationRepeat(p0: android.animation.Animator?) {}

    override fun onAnimationEnd(p0: android.animation.Animator?) {
        activity.after_animation_view.visibility = View.VISIBLE
    }

    override fun onAnimationCancel(p0: android.animation.Animator?) {}

    override fun onAnimationStart(p0: android.animation.Animator?) {}
}