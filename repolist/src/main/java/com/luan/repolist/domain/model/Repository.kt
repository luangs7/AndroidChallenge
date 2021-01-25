package com.luan.repolist.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Repository(
    @SerializedName("full_name")
    val fullName: String? = "",
    @SerializedName("id")
    @PrimaryKey
    val id: Int? = 0,
    @SerializedName("private")
    val private: Boolean = false
)
