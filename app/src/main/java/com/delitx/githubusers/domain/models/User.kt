package com.delitx.githubusers.domain.models

data class User(
    val id: Int,
    val name: String,
    val iconUrl: String,
    val url: String,
    val publicRepositories: Int,
    val publicGists: Int,
    val followers: Int,
)
