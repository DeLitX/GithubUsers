package com.delitx.githubusers.domain.repository

import com.delitx.githubusers.common.DataState
import com.delitx.githubusers.domain.models.BriefUserInfo
import com.delitx.githubusers.domain.models.User
import kotlinx.coroutines.flow.StateFlow

interface UsersRepository {
    val users: StateFlow<List<BriefUserInfo>>
    suspend fun refreshUsers(): DataState<Unit>
    suspend fun loadMoreUsers(): DataState<Unit>
    suspend fun loadUser(userLogin: String): DataState<User>
}
