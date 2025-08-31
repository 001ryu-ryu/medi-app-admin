package com.iftikar.mediadmin.domain.repository

import com.iftikar.mediadmin.data.remote.ApiOperation
import com.iftikar.mediadmin.data.remote.model.ModifyUserResponse
import com.iftikar.mediadmin.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getAllUsers(): Flow<ApiOperation<List<User>>>

    fun getSpecificUser(userId: String): Flow<ApiOperation<User>>

    suspend fun approveUser(userId: String, approval: Int): ApiOperation<ModifyUserResponse>

    suspend fun blockUser(userId: String, blockModify: Int): ApiOperation<ModifyUserResponse>
}