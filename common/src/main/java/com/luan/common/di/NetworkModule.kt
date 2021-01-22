package com.luan.common.di


import com.luan.common.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkModule {
    companion object {
        val dependencyModule = module {
            single {
                provideOkHttpClient()
            }
            single { provideRetrofit(get()) }
        }

        private fun provideOkHttpClient(): OkHttpClient {
            val builder = OkHttpClient.Builder()
                .followRedirects(true)
            if (BuildConfig.DEBUG) {
                builder.addNetworkInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
            return builder.build()
        }


        private fun provideRetrofit(
            client: OkHttpClient
        ): Retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}
