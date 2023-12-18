package com.beni.core.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.beni.core.util.ConstantVariable.ENTITY_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMultipleData(listEntity: List<PatientEntitiy>)

    @Query("SELECT * FROM $ENTITY_NAME")
    fun getAllData(): Flow<List<PatientEntitiy>>

    @Query("SELECT * FROM $ENTITY_NAME WHERE id = :id")
    fun getDataById(id: Int): Flow<PatientEntitiy>

    @Query("DELETE FROM $ENTITY_NAME")
    suspend fun removeAllItem()
}