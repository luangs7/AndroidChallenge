package com.luan.repolist.helper

import com.luan.repolist.domain.model.Repository
import kotlin.random.Random

object DataHelper {

    const val USER_REPO = "luangs7"
    val repository = Repository(USER_REPO, Random.nextInt())
    val listOfRepository = listOf(repository)
}