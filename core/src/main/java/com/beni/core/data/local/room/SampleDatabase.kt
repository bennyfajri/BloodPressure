package com.beni.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        PatientEntitiy::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class SampleDatabase : RoomDatabase() {
    abstract fun sampleDao(): PatientDao
}