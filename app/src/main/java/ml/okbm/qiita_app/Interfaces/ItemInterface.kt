package ml.okbm.qiita_app.Interfaces

import retrofit2.http.GET
import retrofit2.Call
import retrofit2.Retrofit
import ml.okbm.qiita_app.models.Item
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory

interface ItemInterface {
    @GET("v2/items.json")
    fun items(): Call<List<Item>>
}

fun createService(): ItemInterface {
    val baseApiUrl = "https://qiita.com/api/"

    val httpLogging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val httpClientBuilder = OkHttpClient.Builder().addInterceptor(httpLogging)

    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseApiUrl)
        .client(httpClientBuilder.build())
        .build()

    return retrofit.create(ItemInterface::class.java)
}
