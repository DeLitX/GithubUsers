package com.delitx.githubusers.di

import com.delitx.githubusers.domain.network.UsersRequests
import com.delitx.githubusers.domain.repository.UsersRepository
import com.delitx.githubusers.domain.repository.UsersRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UsersRepositoryModule {
    @Singleton
    @Provides
    fun provideUsersRepository(
        usersRequests: UsersRequests,
    ): UsersRepository = UsersRepositoryImpl(
        usersRequests
    )
}
