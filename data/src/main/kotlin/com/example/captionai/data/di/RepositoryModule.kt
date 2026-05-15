package com.example.captionai.data.di

import com.example.captionai.data.repository.CaptionAIRepositoryImpl
import com.example.captionai.domain.repository.CaptionAIRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCaptionAIRepository(
        repositoryImpl: CaptionAIRepositoryImpl
    ): CaptionAIRepository
}
