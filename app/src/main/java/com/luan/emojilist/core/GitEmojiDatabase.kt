package com.luan.emojilist.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luan.emojilist.data.dao.GitEmojiDao
import com.luan.emojilist.domain.model.Emoji

@Database(
    entities = [Emoji::class],
    version = 1)
abstract class GitEmojiDatabase : RoomDatabase() {
    abstract val emojiDao: GitEmojiDao
}