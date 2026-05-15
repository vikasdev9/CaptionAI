package com.example.captionai.data.di

import com.example.captionai.data.repository.CaptionAIRepositoryImpl
import com.example.captionai.data.repository.ProfileRepositoryImpl
import com.example.captionai.data.repository.SettingsRepositoryImpl
import com.example.captionai.domain.repository.CaptionAIRepository
import com.example.captionai.domain.repository.ProfileRepository
import com.example.captionai.domain.repository.SettingsRepository
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

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(
        settingsRepositoryImpl: SettingsRepositoryImpl
    ): SettingsRepository

    @Binds
    @Singleton
    abstract fun bindProfileRepository(
        profileRepositoryImpl: ProfileRepositoryImpl
    ): ProfileRepository
}
