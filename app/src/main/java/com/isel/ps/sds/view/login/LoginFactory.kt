package com.isel.ps.sds.view.login

import android.util.Base64
import android.util.Log
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class LoginFactory {
    fun getLogin(userName: String, pw: String) = Login(userName, encrypt(pw))

    private fun encrypt(pw: String): String {
        val ENCRYPTION_KEY = "POLICARPO É GAY!"
        val input = pw.toByteArray((charset("utf-8")))
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
        return query.substring(0, query.length - 1) // encodeToString ends with "\n"
    }

    class Login(
        val userName: String,
        val password: String
    ) {
        override fun toString(): String {
            return "{\"userName\":\"$userName\", \"password\":\"$password\"}"
        }
    }
}
