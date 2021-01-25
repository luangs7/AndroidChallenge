package com.luan.avatarlist.domain.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class GitUser(
    @SerializedName("avatar_url")
    val avatarUrl: String? = "",
    @SerializedName("id")
    @PrimaryKey
    val id: Int? = 0,
    @SerializedName("login")
    val login: String? = ""
)