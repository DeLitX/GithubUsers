package com.delitx.githubusers.domain.models.dto

import com.delitx.githubusers.domain.models.BriefUserInfo
import com.google.gson.annotations.SerializedName

data class BriefUserInfoDto(
    val id: Int,
    @SerializedName("login")
    val name: String,
    @SerializedName("avatar_url")
    val iconUrl: String,
) {
    fun toModel(): BriefUserInfo = BriefUserInfo(id, name, iconUrl)
}
