package com.example.studykotlin

import com.taoliu.lib_secrets.Algorithm
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object EncryptUtil {
    //AES加密
    fun aes(
        input: String,
        key: String,
        iv: String? = null,
        algorithm: String = Algorithm.AES.CBC,
        paddings: String? = Algorithm.AES.Paddings.PKCS5Padding,
    ): String { //初始化cipher对象
        val cipher =
            Cipher.getInstance(if (paddings.isNullOrEmpty()) algorithm else "$algorithm/$paddings") // 生成密钥
        val keySpec = SecretKeySpec(key.toByteArray(Charsets.UTF_8), "AES")
        if (iv != null) {
            val ivParameterSpec = IvParameterSpec(iv.toByteArray(Charsets.UTF_8))
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec)
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, keySpec) //加密解密
        }
        val encrypt = cipher.doFinal(input.toByteArray(Charsets.UTF_8))
        return Base64Util.encode(encrypt)
    }

    //AES加密
    fun decrypt(
        input: String,
        key: String,
        iv: String? = null,
        algorithm: String = Algorithm.AES.CBC,
        paddings: String? = Algorithm.AES.Paddings.PKCS5Padding,
    ): String { //初始化cipher对象
        val cipher =
            Cipher.getInstance(if (paddings.isNullOrEmpty()) algorithm else "$algorithm/$paddings") // 生成密钥
        val keySpec = SecretKeySpec(key.toByteArray(), "AES")
        if (iv != null) {
            val ivParameterSpec = IvParameterSpec(iv.toByteArray())
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec)
        } else {
            cipher.init(Cipher.DECRYPT_MODE, keySpec) //加密解密
        }
        val data = Base64Util.decode(input)
        val decryptData = cipher.doFinal(data)
        return String(decryptData)
    }

    fun rsa(
        input: String,
        key: String,
        iv: String? = null,
        algorithm: String = Algorithm.RSA.ECB,
        paddings: String? = Algorithm.RSA.Paddings.PKCS1Padding,
    ): String {
        val cipher =
            Cipher.getInstance(if (paddings.isNullOrEmpty()) algorithm else "$algorithm/$paddings") // 生成密钥
        val keyFactory = KeyFactory.getInstance("RSA")
        val publicKey = keyFactory.generatePublic(X509EncodedKeySpec(Base64Util.decode(key)))
        if (iv != null) {
            val ivParameterSpec = IvParameterSpec(iv.toByteArray())
            cipher.init(Cipher.ENCRYPT_MODE, publicKey, ivParameterSpec)
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, publicKey) //加密解密
        }
        val encrypt = cipher.doFinal(input.toByteArray())
        return Base64Util.encode(encrypt)
    }


}