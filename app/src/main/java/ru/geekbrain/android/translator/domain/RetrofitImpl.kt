package ru.geekbrain.android.translator.domain

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.geekbrain.android.translator.data.Word
import ru.geekbrain.android.translator.model.ApiService
import ru.geekbrain.android.translator.ui.TranslatorContract

class RetrofitImpl : TranslatorContract.DataSource<List<Word>> {
    companion object{
        private const val BASE_URL_LOCATIONS =
            "https://dictionary.skyeng.ru/api/public/v1/"

    }

    override fun getWord(searchText: String): Observable<List<Word>> =
        getService(BaseInterceptor.interceptor).search(searchText)


    private fun getService(interceptor: Interceptor): ApiService =
        createRetrofit(interceptor).create(ApiService::class.java)


    private fun createRetrofit(interceptor: Interceptor): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL_LOCATIONS)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(createOkHttpClient(interceptor))
            .build()

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient{
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }

}
