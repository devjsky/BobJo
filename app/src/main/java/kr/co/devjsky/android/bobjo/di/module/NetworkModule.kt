package kr.co.devjsky.android.bobjo.di.module

import kr.co.devjsky.android.bobjo.api.ApiService
import kr.co.devjsky.android.bobjo.data.Constants.Companion.API_SERVER_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory {
        getHttpLoggingInterceptor()
    }

    factory {
        getHttpClient(get())
    }

    factory {
        getGsonConverterFactory()
    }

    factory {
        getRetrofitAPI(get(), get())
    }

    single {
        getNetworkService(get())
    }
}

fun getHttpLoggingInterceptor(): Interceptor = HttpLoggingInterceptor().apply {
    setLevel(HttpLoggingInterceptor.Level.BODY)
}

fun getHttpClient(interceptor: Interceptor) = OkHttpClient.Builder()
    .addInterceptor(interceptor)
    .build()

fun getGsonConverterFactory(): Converter.Factory = GsonConverterFactory.create()
//    .baseUrl("https://www.soundleader.co.kr/webrtc/API1/")

fun getRetrofitAPI(factory: Converter.Factory, client: OkHttpClient): Retrofit = Retrofit.Builder()
    .baseUrl(API_SERVER_URL)
    .addConverterFactory(factory)
    .client(client)
    .build()

fun getNetworkService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)