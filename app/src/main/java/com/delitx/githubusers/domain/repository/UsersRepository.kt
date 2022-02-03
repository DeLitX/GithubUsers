package com.delitx.githubusers.domain.repository

import com.delitx.githubusers.common.DataState
import com.delitx.githubusers.domain.models.BriefUserInfo
import com.delitx.githubusers.domain.models.User
import com.delitx.githubusers.domain.network.UsersRequests
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UsersRepository(
    private val _usersRequests: UsersRequests
) {
    private val _users: MutableStateFlow<List<BriefUserInfo>> = MutableStateFlow(listOf())
    val users: StateFlow<List<BriefUserInfo>> = _users.asStateFlow()

    suspend fun refreshUsers(): DataState<Unit> {
        return try {
            val newUsers = _usersRequests.getUsers()
            _users.emit(newUsers.map { it.toModel() })
            DataState.Data(Unit)
        } catch (e: Exception) {
            DataState.Failure()
        }
    }

    suspend fun loadMoreUsers(): DataState<Unit> {
        return try {
            val newUsers = _usersRequests.getUsers(_users.value.last().id)
            _users.emit(_users.value + newUsers.map { it.toModel() })
            DataState.Data(Unit)
        } catch (e: Exception) {
            DataState.Failure()
        }
    }

    suspend fun loadUser(userLogin: String): DataState<User> {
        return try {
            val user = _usersRequests.getUser(userLogin)
            DataState.Data(user.toModel())
        } catch (e: Exception) {
            DataState.Failure()
        }
    }
}
