package com.masterplus.mesnevi.core.domain.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.masterplus.mesnevi.core.data.ConnectivityProviderImpl
import com.masterplus.mesnevi.core.data.local.AppDatabase
import com.masterplus.mesnevi.core.data.local.TransactionProvider
import com.masterplus.mesnevi.core.data.local.preferences.AppPreferencesImpl
import com.masterplus.mesnevi.core.domain.ConnectivityProvider
import com.masterplus.mesnevi.core.domain.preferences.AppPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application) =
        Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            name = "mesneviDb.db"
        )
            .createFromAsset("mesneviDb.db")
            .addCallback(object: RoomDatabase.Callback(){
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    db.execSQL("INSERT INTO contentsfts(contentsfts) VALUES ('rebuild')")
                }
            })
            .build()


    @Provides
    @Singleton
    fun provideSharedPreferences(application: Application): SharedPreferences =
        application.getSharedPreferences("AppPreferences",MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideAppPreferences(sharedPreferences: SharedPreferences): AppPreferences =
        AppPreferencesImpl(sharedPreferences)

    @Provides
    @Singleton
    fun provideTransactionProvider(db: AppDatabase) = TransactionProvider(db)

    @Provides
    @Singleton
    fun provideConnectivityProvider(application: Application): ConnectivityProvider =
        ConnectivityProviderImpl(application)

}