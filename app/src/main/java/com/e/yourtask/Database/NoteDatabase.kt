package com.e.yourtask.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.e.yourtask.Database.Dao.NoteDao
import com.e.yourtask.Database.Entity.Note

@Database(entities = [Note::class],version = 1)
public abstract  class NoteDatabase : RoomDatabase() {

    abstract fun noteDao() : NoteDao

    companion object
    {
        @Volatile
        private var INSTANCE: NoteDatabase? = null


        fun getDatabase(context: Context): NoteDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}