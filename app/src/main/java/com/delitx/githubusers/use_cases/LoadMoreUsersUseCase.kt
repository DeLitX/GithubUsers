package com.delitx.githubusers.use_cases

import com.delitx.githubusers.common.DataState
import com.delitx.githubusers.domain.repository.UsersRepository

class LoadMoreUsersUseCase(
    private val _repository: UsersRepository
) {
    suspend operator fun invoke(): DataState<Unit> = _repository.loadMoreUsers()
}
