package com.beni.core.data.remote

import android.util.Log
import com.beni.core.data.Resource
import com.beni.core.data.remote.network.ApiService
import com.beni.core.data.remote.request.ExampleRequest
import com.beni.core.data.remote.response.ExampleResponse
import com.beni.core.util.ConstantFunction.getErrorMessage
import com.beni.core.util.ConstantVariable.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    fun sampleGetRequest(): Flow<Resource<List<ExampleResponse>?>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiService.sampleGetRequest()
                if (response.status == 200) {
                    emit(Resource.Success(response.listSampleResponse))
                } else {
                    emit(Resource.Error(response.message?.getErrorMessage().toString()))
                }
            } catch (e: Exception) {
                val errorBody = (e as HttpException).response()?.errorBody()?.string()
                val errorMessage = errorBody?.getErrorMessage().toString()
                emit(Resource.Error(errorMessage))
                Log.d(TAG, "sampleGetRequest: $errorMessage")
            }
        }.flowOn(Dispatchers.IO)
    }

    fun sampleInsertData(
        header: String,
        sampleRequest: ExampleRequest
    ): Flow<Resource<ExampleResponse?>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiService.sampleInsertData(header, sampleRequest)

                if (response.status == 200) {
                    emit(Resource.Success(response.sampleResponse))
                } else {
                    emit(Resource.Error(response.message?.getErrorMessage().toString()))
                }
            } catch (e: Exception) {
                val errorBody = (e as HttpException).response()?.errorBody()?.string()
                val errorMessage = errorBody?.getErrorMessage().toString()
                emit(Resource.Error(errorMessage))
                Log.d(TAG, "sampleInsertData: $errorMessage")
            }
        }.flowOn(Dispatchers.IO)
    }

    fun sampleInsertData(
        header: String,
        field1: String,
        field2: String
    ): Flow<Resource<ExampleResponse?>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiService.sampleInsertData(header, field1, field2)

                if (response.status == 200) {
                    emit(Resource.Success(response.sampleResponse))
                } else {
                    emit(Resource.Error(response.message?.getErrorMessage().toString()))
                }
            } catch (e: Exception) {
                val errorBody = (e as HttpException).response()?.errorBody()?.string()
                val errorMessage = errorBody?.getErrorMessage().toString()
                emit(Resource.Error(errorMessage))
                Log.d(TAG, "sampleInsertData: $errorMessage")
            }
        }.flowOn(Dispatchers.IO)
    }

    fun sampleInsertSingleImage(
        header: String,
        image: MultipartBody.Part,
        field1: RequestBody,
        field2: RequestBody,
    ): Flow<Resource<ExampleResponse?>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiService.sampleInsertSingleImage(header, image, field1, field2)

                if (response.status == 200) {
                    emit(Resource.Success(response.sampleResponse))
                } else {
                    emit(Resource.Error(response.message?.getErrorMessage().toString()))
                }
            } catch (e: Exception) {
                val errorBody = (e as HttpException).response()?.errorBody()?.string()
                val errorMessage = errorBody?.getErrorMessage().toString()
                emit(Resource.Error(errorMessage))
                Log.d(TAG, "sampleInsertSingleImage: $errorMessage")
            }
        }.flowOn(Dispatchers.IO)
    }

    fun sampleInsertMultipleImage(
        header: String,
        image: List<MultipartBody.Part>,
        field1: RequestBody,
        field2: RequestBody,
    ): Flow<Resource<ExampleResponse?>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiService.sampleInsertMultipleImage(header, image, field1, field2)

                if (response.status == 200) {
                    emit(Resource.Success(response.sampleResponse))
                } else {
                    emit(Resource.Error(response.message?.getErrorMessage().toString()))
                }
            } catch (e: Exception) {
                val errorBody = (e as HttpException).response()?.errorBody()?.string()
                val errorMessage = errorBody?.getErrorMessage().toString()
                emit(Resource.Error(errorMessage))
                Log.d(TAG, "sampleInsertMultipleImage: $errorMessage")
            }
        }.flowOn(Dispatchers.IO)
    }
}