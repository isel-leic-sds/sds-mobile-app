package com.isel.ps.sds.view.login

import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.LoginViewModel
import com.isel.ps.sds.R
import com.isel.ps.sds.view.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class LoginActivity : BaseActivity<LoginViewModel>() {

    override fun defineViewModel(): Class<LoginViewModel> = LoginViewModel::class.java
    override fun layoutToInflate(): Int = R.layout.activity_login

    override fun doOnCreate(savedInstanceState: Bundle?) {

        loginBtn.setOnClickListener {
            var userName : String = userName.text.toString()
            var password = password.text.toString()
            var userID = userID.text.toString() // encrypt(password.text.toString())

            viewModel.init(userName,password,userID)


            //loginVM.getLogin()?.observe(this, Observer { login -> if(login.accepted) // Saltar para a Activity Menu
            // caso contrário mostrar o erro ao utilizador }
            // Toast.makeText(this,"Credentials are not correct",Toast.LENGTH_SHORT).show()


            /*
            val i: Intent = Intent(this, MenuActivity::class.java)
            startActivity(i)
            */
        }

}




    fun encrypt(pw: String) : String{
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

        val query= Base64.encodeToString(cipherText,Base64.DEFAULT)
        Log.i("LoginActivity", query)
        return query
    }
}
