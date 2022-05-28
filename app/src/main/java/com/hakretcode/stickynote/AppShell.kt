package com.hakretcode.stickynote

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.hakretcode.stickynote.data.db.NoteDao
import com.hakretcode.stickynote.data.db.NoteDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@HiltAndroidApp
class AppShell : Application()

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {
    @Singleton
    @Provides
    fun database(@ApplicationContext context: Context): NoteDao =
        Room.databaseBuilder(context, NoteDatabase::class.java,
            "note_database").build().get()
    @Provides
    fun coroutineContext(): CoroutineContext = Dispatchers.IO
}