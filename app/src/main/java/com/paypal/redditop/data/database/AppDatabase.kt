package com.paypal.redditop.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.paypal.redditop.data.database.entities.PostEntity

@Database(entities = [PostEntity::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}