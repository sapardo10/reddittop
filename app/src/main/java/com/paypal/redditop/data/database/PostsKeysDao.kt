package com.paypal.redditop.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.paypal.redditop.models.PostsKeys

@Dao
interface PostsKeysDao {

    @Insert(onConflict = REPLACE)
    suspend fun savePostsKeys(redditKey: PostsKeys)

    @Query("SELECT * FROM keys ORDER BY id DESC")
    suspend fun getPostsKeys(): List<PostsKeys>
}
