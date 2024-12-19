package com.dingstock.net

import com.dingstock.net.model.Env
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

internal fun createRetrofit(okHttpClient: OkHttpClient, env: Env): Retrofit {
    return Retrofit.Builder().client(okHttpClient).baseUrl(env.baseUrl)
        .addConverterFactory(com.dingstock.net.convert.MoshiConverterFactory.create()).build()
}

internal fun createOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder().sslSocketFactory(sslFactory(), trustManagers()[0] as X509TrustManager)
        .hostnameVerifier { hostname, session -> true }
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build()
}

internal fun trustManagers(): Array<TrustManager> { // Create a trust manager that does not validate certificate chains
    val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        @Throws(CertificateException::class)
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return arrayOf()
        }
    })
    return trustAllCerts
}

internal fun sslFactory(): SSLSocketFactory {

    // Install the all-trusting trust manager
    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, trustManagers(), SecureRandom())

    return sslContext.socketFactory
}