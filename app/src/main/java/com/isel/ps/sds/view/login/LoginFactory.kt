package com.isel.ps.sds.view.login

/**
 * See: https://github.com/luke-park/SecureCompatibleEncryptionExamples/blob/master/Kotlin/SCEE.kt
 */

import android.util.Base64
import java.nio.charset.StandardCharsets
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class LoginFactory {

    private val ALGORITHM_NAME: String = "AES/GCM/NoPadding"
    private val ALGORITHM_KEY_NAME: String = "AES"
    private val ALGORITHM_NONCE_SIZE: Int = 12
    private val ALGORITHM_TAG_SIZE: Int = 128
    private val ALGORITHM_KEY_SIZE: Int = 128
    private val PBKDF2_NAME: String = "PBKDF2withHmacSHA1And8BIT"
    private val PBKDF2_SALT_SIZE: Int = 16
    private val PBKDF2_ITERATIONS = 32767

    private val CRYPTO_KEY = "SDS_ISEL_P3_G21_1819v"

    fun getLogin(userName: String, pw: String) = Login(userName, encrypt(pw))

//    private fun encrypt(pw: String): String {
//        val plaintext: ByteArray = pw.toByteArray()
//        val keygen = KeyGenerator.getInstance("AES")
//        keygen.init(256)
//        val key: SecretKey = keygen.generateKey()
//        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
//        cipher.init(Cipher.ENCRYPT_MODE, key)
//        val ciphertext: ByteArray = cipher.doFinal(plaintext)
//        val iv: ByteArray = cipher.iv
//        return ""
//    }

    private fun encrypt(plaintext: String): String {
        fun encryptBuffer(text: ByteArray, key: ByteArray): ByteArray {
            // Generate a 96-bit nonce using a CSPRNG.
            val rand = SecureRandom()
            val nonce = ByteArray(ALGORITHM_NONCE_SIZE)
            rand.nextBytes(nonce)

            // Create the cipher instance and initialize.
            val cipher: Cipher = Cipher.getInstance(ALGORITHM_NAME)
            cipher.init(
                Cipher.ENCRYPT_MODE,
                SecretKeySpec(key, ALGORITHM_KEY_NAME),
                GCMParameterSpec(ALGORITHM_TAG_SIZE, nonce)
            )

            // Encrypt and prepend nonce.
            val ciphertext: ByteArray = cipher.doFinal(text)
            val ciphertextAndNonce = ByteArray(nonce.size + ciphertext.size)
            System.arraycopy(nonce, 0, ciphertextAndNonce, 0, nonce.size)
            System.arraycopy(ciphertext, 0, ciphertextAndNonce, nonce.size, ciphertext.size)

            return ciphertextAndNonce
        }

        // Generate a 128-bit salt using a CSPRNG.
        val rand = SecureRandom()
        val salt = ByteArray(PBKDF2_SALT_SIZE)
        rand.nextBytes(salt)

        // Create an instance of PBKDF2 and derive a key.
        val pwSpec = PBEKeySpec(CRYPTO_KEY.toCharArray(), salt, PBKDF2_ITERATIONS, ALGORITHM_KEY_SIZE)
        val keyFactory: SecretKeyFactory = SecretKeyFactory.getInstance(PBKDF2_NAME)
        val key: ByteArray = keyFactory.generateSecret(pwSpec).getEncoded()

        // Encrypt and prepend salt.
        val ciphertextAndNonce: ByteArray = encryptBuffer(plaintext.toByteArray(StandardCharsets.UTF_8), key)
        val ciphertextAndNonceAndSalt = ByteArray(salt.size + ciphertextAndNonce.size)
        System.arraycopy(salt, 0, ciphertextAndNonceAndSalt, 0, salt.size)
        System.arraycopy(ciphertextAndNonce, 0, ciphertextAndNonceAndSalt, salt.size, ciphertextAndNonce.size)

        return Base64.encodeToString(ciphertextAndNonceAndSalt, Base64.DEFAULT)
    }

    class Login(
        val sdsID: String,
        val password: String
    ) {
        override fun toString(): String {
            return "{\"sdsID\":\"$sdsID\", \"password\":\"$password\"}"
        }
    }
}
