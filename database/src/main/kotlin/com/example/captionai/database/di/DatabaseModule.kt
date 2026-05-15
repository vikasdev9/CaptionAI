package com.example.captionai.database.di

import android.content.Context
import androidx.room.Room
import com.example.captionai.core.Constants
import com.example.captionai.database.CaptionAIDatabase
import com.example.captionai.database.dao.PlannerDao
import com.example.captionai.database.dao.SavedContentDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CaptionAIDatabase {
        return Room.databaseBuilder(
            context,
            CaptionAIDatabase::class.java,
            Constants.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideSavedContentDao(db: CaptionAIDatabase): SavedContentDao = db.savedContentDao()

    @Provides
    fun providePlannerDao(db: CaptionAIDatabase): PlannerDao = db.plannerDao()
}
