package com.iftikar.mediadmin.domain.usecase

import com.iftikar.mediadmin.domain.repository.UserRepository
import javax.inject.Inject

class ApproveUserUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(userId: String, approval: Int) =
        userRepository.approveUser(userId = userId, approval = approval)
}