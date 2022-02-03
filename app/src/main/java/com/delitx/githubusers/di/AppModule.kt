package com.delitx.githubusers.di

import android.content.Context
import com.delitx.githubusers.GithubUsersApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): GithubUsersApplication =
        app as GithubUsersApplication
}
