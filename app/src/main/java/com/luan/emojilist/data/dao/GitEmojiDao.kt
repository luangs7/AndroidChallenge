package com.luan.emojilist.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.luan.emojilist.domain.model.Emoji
import kotlinx.coroutines.flow.Flow

@Dao
interface GitEmojiDao {

    @Query("SELECT * FROM emoji")
    suspend fun getList(): Flow<List<Emoji>>

    @Query("SELECT * FROM emoji ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomEmoji(): Flow<Emoji>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveEmojiList(list:List<Emoji>)
}