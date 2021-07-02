package com.paypal.redditop.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.paypal.redditop.models.PostsKeys
import com.paypal.redditop.models.SimplePost

@Database(entities = [SimplePost::class, PostsKeys::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun postKeysDao(): PostsKeysDao
}