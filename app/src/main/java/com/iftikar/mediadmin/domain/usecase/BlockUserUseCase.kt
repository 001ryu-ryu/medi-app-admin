package com.iftikar.mediadmin.domain.usecase

import com.iftikar.mediadmin.domain.repository.UserRepository
import javax.inject.Inject

class BlockUserUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(userId: String, modifyBlock: Int)
    = userRepository.blockUser(userId, modifyBlock)
}