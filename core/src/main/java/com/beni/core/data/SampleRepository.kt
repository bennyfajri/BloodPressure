package com.beni.core.data

import com.beni.core.data.local.LocalDataSource
import com.beni.core.data.local.room.PatientEntitiy
import com.beni.core.data.remote.RemoteDataSource
import com.beni.core.data.remote.request.ExampleRequest
import com.beni.core.util.ConstantFunction.toListMultipartBodyParamter
import com.beni.core.util.ConstantFunction.toMultipartBodyParamter
import com.beni.core.util.ConstantFunction.toRequestBodyParameter
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class SampleRepository @Inject constructor(
    private val remoteData: RemoteDataSource,
    private val localData: LocalDataSource
) {
    suspend fun insertMultipleData(listEntity: List<PatientEntitiy>) = localData.insertMultipleData(listEntity)

    fun getAllData(): Flow<List<PatientEntitiy>> = localData.getAllData()

    fun getDataById(id: Int): Flow<PatientEntitiy> = localData.getDataById(id)

    suspend fun removeAllItem() = localData.removeAllItem()
}