package com.beni.core.di

import android.content.Context
import androidx.room.Room
import com.beni.core.BuildConfig
import com.beni.core.data.local.room.PatientDao
import com.beni.core.data.local.room.SampleDatabase
import com.beni.core.data.remote.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        //add if needed certificate pinner
//        val hostName = "apisekarang.diatakasir.com"
//        val certificatePinner = CertificatePinner.Builder()
//            .add(hostName, "sha256/O/QJLSgw7O5AZe847zJFXmgMEKZW8fDBvrT4X0ZiKk8=")
//            .add(hostName, "sha256/jQJTbIh0grw0/1TkHSumWb+Fs0Ggogr621gT3PvPKG0=")
//            .add(hostName, "sha256/C5+lpZ7tcVwmwQIMcRtPbsQtWLABXhQzejna0wHFr8M=")
//            .build()

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
//            .certificatePinner(certificatePinner)
            .build()

        val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): SampleDatabase = Room.databaseBuilder(
        context,
        SampleDatabase::class.java, "BloodPresure.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideQuestionDao(database: SampleDatabase): PatientDao =
        database.sampleDao()
}