package com.example.captionai.network.di

import com.google.ai.client.generativeai.GenerativeModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideGenerativeModel(): GenerativeModel {
        // API Key will be injected via BuildConfig or similar in the app module
        return GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = "YOUR_API_KEY" // Placeholder, will be handled properly in app module
        )
    }
}
