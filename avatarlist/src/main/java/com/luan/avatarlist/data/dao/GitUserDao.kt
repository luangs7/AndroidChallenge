package com.luan.avatarlist.data.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.luan.avatarlist.domain.model.GitUser
import com.luan.common.domain.Emoji
import kotlinx.coroutines.flow.Flow

interface GitUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user:GitUser)

    @Query("SELECT * FROM gituser WHERE login = :_login")
    suspend fun getUser( _login:String ):Flow<GitUser?>

    @Query("SELECT * FROM gituser")
    suspend fun getListUser():Flow<List<GitUser>>

}