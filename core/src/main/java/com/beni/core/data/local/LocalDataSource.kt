package com.beni.core.data.local

import com.beni.core.data.local.room.PatientDao
import com.beni.core.data.local.room.PatientEntitiy
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val room: PatientDao
) {

    suspend fun insertMultipleData(listEntity: List<PatientEntitiy>) = room.insertMultipleData(listEntity)

    fun getAllData(): Flow<List<PatientEntitiy>>  = room.getAllData()

    fun getDataById(id: Int): Flow<PatientEntitiy> = room.getDataById(id)

    suspend fun removeAllItem() = room.removeAllItem()
}