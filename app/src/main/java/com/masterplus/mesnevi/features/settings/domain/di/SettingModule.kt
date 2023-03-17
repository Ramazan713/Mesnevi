package com.masterplus.mesnevi.features.settings.domain.di

import com.masterplus.mesnevi.features.settings.data.GsonParser
import com.masterplus.mesnevi.features.settings.domain.JsonParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingModule {

    @Provides
    @Singleton
    fun provideJsonParser(): JsonParser = GsonParser()
}