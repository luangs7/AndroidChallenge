package com.luan.avatarlist.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luan.avatarlist.domain.model.GitUser
import com.luan.common.domain.Emoji

@Database(
    entities = [GitUser::class],
    version = 1,
    exportSchema = false
)
abstract class GitUserDatabase : RoomDatabase() {
    abstract val gitUserDao: GitUserDao
}