package com.example.to_docompose.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.to_docompose.data.DataBase
import com.example.to_docompose.util.Constants.DATA_BASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideDB(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        DataBase::class.java,
        DATA_BASE_NAME
    ).build()

    @Singleton
    @Provides
    fun providesDao(appDataBase: DataBase) = appDataBase.toDoDao()
}