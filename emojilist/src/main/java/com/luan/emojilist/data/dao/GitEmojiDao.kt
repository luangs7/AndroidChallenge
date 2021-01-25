package com.luan.emojilist.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.luan.common.domain.Emoji
import kotlinx.coroutines.flow.Flow

@Dao
interface GitEmojiDao {

    @Query("SELECT * FROM emoji")
     fun getList(): Flow<List<Emoji>>

    @Query("SELECT * FROM emoji ORDER BY RANDOM() LIMIT 1")
     fun getRandomEmoji(): Flow<Emoji>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun saveEmojiList(list:List<Emoji>)
}