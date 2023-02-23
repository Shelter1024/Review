package com.taoliu.lib_secrets

sealed class Algorithm {
    object AES {
        @JvmStatic
        val CBC: String = "AES/CBC"

        @JvmStatic
        val CFB: String = "AES/CFB"

        @JvmStatic
        val CTR: String = "AES/CTR"

        @JvmStatic
        val CTS: String = "AES/CTS"

        @JvmStatic
        val ECB: String = "AES/ECB"

        @JvmStatic
        val OFB: String = "AES/OFB"

        object Paddings {
            @JvmStatic
            val ISO10126Padding: String = "ISO10126Padding"

            @JvmStatic
            val NoPadding: String = "NoPadding"

            @JvmStatic
            val PKCS5Padding: String = "PKCS5Padding"
        }
    }

    object RSA {
        @JvmStatic
        val ECB: String = "RSA/ECB"

        object Paddings {
            @JvmStatic
            val PKCS1Padding: String = "PKCS1Padding"
        }
    }
}
