package com.iftikar.mediadmin.domain.usecase

import com.iftikar.mediadmin.domain.repository.UserRepository
import javax.inject.Inject

class GetSpecificUserUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(userId: String) =
        userRepository.getSpecificUser(userId = userId)
}