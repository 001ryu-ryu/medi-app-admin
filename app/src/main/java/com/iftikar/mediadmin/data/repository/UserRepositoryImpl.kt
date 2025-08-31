package com.iftikar.mediadmin.data.repository

import android.util.Log
import com.iftikar.mediadmin.data.remote.ApiOperation
import com.iftikar.mediadmin.data.remote.ApiService
import com.iftikar.mediadmin.data.remote.model.ModifyUserResponse
import com.iftikar.mediadmin.domain.model.User
import com.iftikar.mediadmin.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apiService: ApiService) : UserRepository {
    override fun getAllUsers(): Flow<ApiOperation<List<User>>> = flow {
        try {
            val response = apiService.getAllUsers()
            if (response.isSuccessful) {
                val users = response.body()
                if (users != null) {
                    emit(ApiOperation.Success(users))
                } else {
                    emit(ApiOperation.Failure(NullPointerException("User not found")))
                }
            } else {
                emit(ApiOperation.Failure(HttpException(response)))
            }
        } catch (e: IOException) {
            emit(ApiOperation.Failure(IOException("Please check your internet connection")))
        } catch (e: Exception) {
            emit(ApiOperation.Failure(e))
        }
    }

    override fun getSpecificUser(userId: String): Flow<ApiOperation<User>> = flow {
        try {
            val response = apiService.getSpecificUser(userId = userId)
            if (response.isSuccessful) {
                val users = response.body()
                val user = users?.lastOrNull() ?: run {
                    emit(ApiOperation.Failure(NullPointerException("User not found")))
                    return@flow
                }
                Log.d("Repo-User", user.isApproved.toString())
                emit(ApiOperation.Success(user))
            } else {
                Log.e("User-Error", response.message())
                emit(ApiOperation.Failure(HttpException(response)))
            }
        } catch (e: Exception) {
            Log.e("User-Error_Out", e.message ?: "Unknown error")
            emit(ApiOperation.Failure(e))
        }
    }

    override suspend fun approveUser(
        userId: String,
        approval: Int
    ): ApiOperation<ModifyUserResponse> {
        val response = apiService.approveUser(userId = userId, approve = approval)
        return try {
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    ApiOperation.Success(
                        data = body
                    )
                } else {
                    ApiOperation.Failure(
                        exception = NullPointerException("Operation failed")
                    )
                }
            } else {
                ApiOperation.Failure(
                    exception = HttpException(response)
                )
            }
        } catch (e: Exception) {
            return ApiOperation.Failure(
                exception = e
            )
        }
    }

    override suspend fun blockUser(
        userId: String,
        blockModify: Int
    ): ApiOperation<ModifyUserResponse> {
        val response = apiService.blockUser(userId, blockModify)
        return try {
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    ApiOperation.Success(body)
                } else {
                    ApiOperation.Failure(NullPointerException("Response not found"))
                }
            } else {
                ApiOperation.Failure(Exception("Operation failed!"))
            }
        } catch (e: Exception) {
            ApiOperation.Failure(Exception("Could not modify the user"))
        }
    }
}






















