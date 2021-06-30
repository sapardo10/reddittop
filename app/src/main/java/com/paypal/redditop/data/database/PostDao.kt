package com.paypal.redditop.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.paypal.redditop.models.SimplePost

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<SimplePost>)

    @Query("SELECT * FROM posts")
    fun getAllPaged(): PagingSource<Int, SimplePost>

    @Query("SELECT * FROM posts WHERE id = :id")
    suspend fun getPost(id: String): SimplePost?

    @Query("DELETE FROM posts")
    suspend fun clearAll()
}