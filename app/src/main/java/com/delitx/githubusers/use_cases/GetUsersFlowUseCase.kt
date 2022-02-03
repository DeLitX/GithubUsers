package com.delitx.githubusers.use_cases

import com.delitx.githubusers.domain.models.BriefUserInfo
import com.delitx.githubusers.domain.repository.UsersRepository
import kotlinx.coroutines.flow.StateFlow

class GetUsersFlowUseCase(
    private val _repository: UsersRepository
) {
    operator fun invoke(): StateFlow<List<BriefUserInfo>> = _repository.users
}
