package com.delitx.githubusers.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delitx.githubusers.common.DataState
import com.delitx.githubusers.domain.models.User
import com.delitx.githubusers.use_cases.LoadUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val _loadUserUseCase: LoadUserUseCase,
) : ViewModel() {
    private val _user: MutableStateFlow<DataState<User>> = MutableStateFlow(DataState.Undefined())
    val user: StateFlow<DataState<User>> = _user.asStateFlow()

    fun loadUser(userLogin: String) {
        viewModelScope.launch {
            val result = _loadUserUseCase(userLogin)
            _user.emit(result)
        }
    }
}
