package com.delitx.githubusers.use_cases

import com.delitx.githubusers.common.DataState
import com.delitx.githubusers.domain.repository.UsersRepository

class RefreshUsersUseCase(
    private val _repository: UsersRepository
) {
    suspend fun invoke(): DataState<Unit> = _repository.refreshUsers()
}
