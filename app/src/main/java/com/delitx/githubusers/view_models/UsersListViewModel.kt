package com.delitx.githubusers.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delitx.githubusers.common.DataState
import com.delitx.githubusers.common.InternetError
import com.delitx.githubusers.use_cases.GetUsersFlowUseCase
import com.delitx.githubusers.use_cases.LoadMoreUsersUseCase
import com.delitx.githubusers.use_cases.RefreshUsersUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class UsersListViewModel(
    private val _refreshUsersUseCase: RefreshUsersUseCase,
    private val _loadMoreUsersUseCase: LoadMoreUsersUseCase,
    private val _getUsersFlowUseCase: GetUsersFlowUseCase,
) : ViewModel() {
    val users = _getUsersFlowUseCase()
    private val _internetError: MutableSharedFlow<InternetError> = MutableSharedFlow()
    val internetError: SharedFlow<InternetError> = _internetError.asSharedFlow()

    private val _isUsersRefreshing: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val isUsersRefreshing: SharedFlow<Boolean> = _isUsersRefreshing.asSharedFlow()

    private val _isMoreUsersLoading: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val isMoreUsersLoading: SharedFlow<Boolean> = _isMoreUsersLoading.asSharedFlow()

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
                    is DataState.Failure -> _internetError.emit(InternetError.DataNotLoaded)
                }
            }
        }
    }

    fun maybeRefreshUsers() {
        if (_refreshUsersJob == null) {
            _refreshUsersJob = viewModelScope.launch {
                val result = _loadMoreUsersUseCase()
                when (result) {
                    is DataState.Data -> {
                        _refreshUsersJob = null
                        _loadMoreUsersJob?.cancel()
                        _loadMoreUsersJob = null
                    }
                    is DataState.Failure -> _internetError.emit(InternetError.DataNotLoaded)
                }
            }
        }
    }
}
