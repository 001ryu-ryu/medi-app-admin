package com.iftikar.mediadmin.data.repository

import android.util.Log
import com.iftikar.mediadmin.data.remote.ApiOperation
import com.iftikar.mediadmin.data.remote.ApiService
import com.iftikar.mediadmin.domain.model.User
import com.iftikar.mediadmin.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.toString

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
}