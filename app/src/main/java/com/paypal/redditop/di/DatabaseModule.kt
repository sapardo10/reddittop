package com.paypal.redditop.di

import android.content.Context
import androidx.room.Room
import com.paypal.redditop.data.database.AppDatabase
import com.paypal.redditop.data.database.PostDao
import com.paypal.redditop.data.database.PostsKeysDao
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
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "PostsDatabase"
        ).build()
    }

    @Provides
    fun providePostsDao(appDatabase: AppDatabase): PostDao {
        return appDatabase.postDao()
    }

    @Provides
    fun providePostsKeysDao(appDatabase: AppDatabase): PostsKeysDao {
        return appDatabase.postKeysDao()
    }
}