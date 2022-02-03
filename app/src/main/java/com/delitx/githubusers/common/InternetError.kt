package com.delitx.githubusers.common

sealed class InternetError {
    object DataNotLoaded : InternetError()
}
