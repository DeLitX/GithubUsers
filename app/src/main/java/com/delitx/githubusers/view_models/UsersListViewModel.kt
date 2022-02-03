package com.delitx.githubusers.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delitx.githubusers.common.DataState
import com.delitx.githubusers.common.InternetError
import com.delitx.githubusers.use_cases.GetUsersFlowUseCase
import com.delitx.githubusers.use_cases.LoadMoreUsersUseCase
import com.delitx.githubusers.use_cases.RefreshUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel @Inject constructor(
    private val _refreshUsersUseCase: RefreshUsersUseCase,
    private val _loadMoreUsersUseCase: LoadMoreUsersUseCase,
    private val _getUsersFlowUseCase: GetUsersFlowUseCase,
) : ViewModel() {
    val users = _getUsersFlowUseCase()
    private val _internetError: MutableSharedFlow<InternetError> = MutableSharedFlow()
    val internetError: SharedFlow<InternetError> = _internetError.asSharedFlow()

    private val _isUsersRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isUsersRefreshing: StateFlow<Boolean> = _isUsersRefreshing.asStateFlow()

    private val _isMoreUsersLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isMoreUsersLoading: StateFlow<Boolean> = _isMoreUsersLoading.asStateFlow()

    private var _loadMoreUsersJob: Job? = null
        set(value) {
            viewModelScope.launch { _isMoreUsersLoading.emit(value != null) }
            field = value
        }
    private var _refreshUsersJob: Job? = null
        set(value) {
            viewModelScope.launch { _isUsersRefreshing.emit(value != null) }
            field = value
        }

    fun maybeLoadMoreUsers() {
        if (_loadMoreUsersJob == null) {
            _loadMoreUsersJob = viewModelScope.launch {
                val result = _loadMoreUsersUseCase()
                when (result) {
                    is DataState.Data -> _loadMoreUsersJob = null
                    is DataState.Failure -> {
                        _loadMoreUsersJob = null
                        _internetError.emit(InternetError.DataNotLoaded)
                    }
                    is DataState.Undefined -> {}
                }
            }
        }
    }

    fun maybeRefreshUsers() {
        if (_refreshUsersJob == null) {
            _refreshUsersJob = viewModelScope.launch {
                val result = _refreshUsersUseCase()
                when (result) {
                    is DataState.Data -> {
                        _refreshUsersJob = null
                        _loadMoreUsersJob?.cancel()
                        _loadMoreUsersJob = null
                    }
                    is DataState.Failure -> {
                        _refreshUsersJob = null
                        _internetError.emit(InternetError.DataNotLoaded)
                    }
                    is DataState.Undefined -> {}
                }
            }
        }
    }
}
