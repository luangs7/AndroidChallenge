package com.luan.avatarlist.data.dao

import androidx.room.*
import com.luan.avatarlist.domain.model.GitUser
import com.luan.common.domain.Emoji
import kotlinx.coroutines.flow.Flow

@Dao
interface GitUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun saveUser(user:GitUser)

    @Query("SELECT * FROM gituser WHERE login = :login")
     fun getUser(login:String):GitUser?

    @Query("SELECT * FROM gituser")
     fun getListUser():Flow<List<GitUser>>

    @Query("DELETE FROM gituser WHERE id = :id")
    fun deleteUser(id:Int)

}