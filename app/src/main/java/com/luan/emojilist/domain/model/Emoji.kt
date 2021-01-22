package com.luan.emojilist.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Emoji(
        @PrimaryKey private val key:String,
        @ColumnInfo(name = "source") private val source:String)