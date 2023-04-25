package about.cats.api

import about.cats.utils.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private fun okHttpClient(): OkHttpClient =
        OkHttpClient.Builder().apply {
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(20, TimeUnit.SECONDS)
            writeTimeout(20, TimeUnit.SECONDS)
        }.build()

    private fun retrofitService(): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(about.cats.api.RetrofitClient.okHttpClient())
            .baseUrl(BASE_URL)
            .build()

    val instance: ApiService by lazy { retrofitService().create(ApiService::class.java) }

}
