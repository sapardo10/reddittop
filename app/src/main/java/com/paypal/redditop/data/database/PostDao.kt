package com.paypal.redditop.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.paypal.redditop.data.database.entities.PostEntity

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<PostEntity>)

    @Query("SELECT * FROM posts")
    fun getAllPaged(): PagingSource<Int, PostEntity>

    @Query("DELETE FROM posts")
    suspend fun clearAll()
}