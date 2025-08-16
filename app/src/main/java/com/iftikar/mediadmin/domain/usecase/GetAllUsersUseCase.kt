package com.iftikar.mediadmin.domain.usecase

import com.iftikar.mediadmin.domain.repository.UserRepository
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke() = userRepository.getAllUsers()
}