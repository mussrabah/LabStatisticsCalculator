package com.musscoding.labstatisticscalculator.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.musscoding.labstatisticscalculator.data.local.Database
import com.musscoding.labstatisticscalculator.data.preferences.DefaultPreference
import com.musscoding.labstatisticscalculator.data.repository.RepositoryImpl
import com.musscoding.labstatisticscalculator.domain.preferences.Preferences
import com.musscoding.labstatisticscalculator.domain.repository.Repository
import com.musscoding.labstatisticscalculator.domain.use_case.AddParameterUseCase
import com.musscoding.labstatisticscalculator.domain.use_case.GetParametersUseCase
import com.musscoding.labstatisticscalculator.domain.use_case.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun providesDatabase(app: Application): Database {
        return Room.databaseBuilder(
            app,
            Database::class.java,
            "lab_stats"
        ).build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun providesRepository(
        db: Database
    ): Repository {
        return RepositoryImpl(
            db.dao
        )
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(
        app: Application
    ): SharedPreferences {
        return app.getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePreferences(sharedPreferences: SharedPreferences): Preferences {
        return DefaultPreference(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: Repository): UseCases {
        return UseCases(
            addParameterUseCase = AddParameterUseCase(repository),
            getParametersUseCase = GetParametersUseCase(repository)
        )
    }
}