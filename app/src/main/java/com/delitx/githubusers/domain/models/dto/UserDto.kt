package com.delitx.githubusers.domain.models.dto

import com.delitx.githubusers.domain.models.User
import com.google.gson.annotations.SerializedName

data class UserDto(
    val id: Int,
    @SerializedName("login")
    val name: String,
    @SerializedName("avatar_url")
    val iconUrl: String,
    @SerializedName("html_url")
    val url: String,
    @SerializedName("public_repos")
    val publicRepositories: Int,
    @SerializedName("public_gists")
    val publicGists: Int,
    @SerializedName("followers")
    val followers: Int,
) {
    fun toModel(): User = User(id, name, iconUrl, url, publicRepositories, publicGists, followers)
}
