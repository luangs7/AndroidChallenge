package com.luan.emojilist.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luan.emojilist.data.dao.GitEmojiDao
import com.luan.common.domain.Emoji

@Database(
    entities = [Emoji::class],
    version = 1,
    exportSchema = false
)
abstract class GitEmojiDatabase : RoomDatabase() {
    abstract val emojiDao: GitEmojiDao
}