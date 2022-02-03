package com.delitx.githubusers.common

sealed class DataState<T> {
    class Data<T>(val value: T) : DataState<T>()
    class Failure<T> : DataState<T>()
}
