package com.hakretcode.stickynote.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hakretcode.stickynote.data.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun get(): NoteDao
}