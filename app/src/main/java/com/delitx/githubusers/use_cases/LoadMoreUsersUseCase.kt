package com.delitx.githubusers.use_cases

import com.delitx.githubusers.common.DataState
import com.delitx.githubusers.domain.repository.UsersRepository
import javax.inject.Inject

class LoadMoreUsersUseCase @Inject constructor(
    private val _repository: UsersRepository
) {
    suspend operator fun invoke(): DataState<Unit> = _repository.loadMoreUsers()
}
