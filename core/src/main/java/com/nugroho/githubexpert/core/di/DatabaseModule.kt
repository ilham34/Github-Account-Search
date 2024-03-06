package com.nugroho.githubexpert.core.di

import android.content.Context
import androidx.room.Room
import com.nugroho.githubexpert.core.data.source.local.room.UserDao
import com.nugroho.githubexpert.core.data.source.local.room.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    val passphrase: ByteArray = SQLiteDatabase.getBytes("nugroho".toCharArray())
    val factory = SupportFactory(passphrase)
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): UserDatabase =
        Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            "UserGithub.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()

    @Provides
    fun provideUserDao(database: UserDatabase): UserDao = database.userDao()
}