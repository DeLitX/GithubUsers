package com.delitx.githubusers.domain.network

import com.delitx.githubusers.domain.models.dto.BriefUserInfoDto
import com.delitx.githubusers.domain.models.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UsersRequests {
    @GET("users")
    suspend fun getUsers(@Query("since") lastUserId: Int = 0): List<BriefUserInfoDto>

    @GET("users/{user}")
    suspend fun getUser(@Path("user") userLogin: String): UserDto
}
