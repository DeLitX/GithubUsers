package com.delitx.githubusers.use_cases

import com.delitx.githubusers.common.DataState
import com.delitx.githubusers.domain.models.User
import com.delitx.githubusers.domain.repository.UsersRepository

class LoadUserUseCase(
    private val _repository: UsersRepository
) {
    suspend operator fun invoke(login: String): DataState<User> = _repository.loadUser(login)
}
