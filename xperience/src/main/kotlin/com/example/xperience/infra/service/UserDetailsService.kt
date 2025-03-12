package com.example.xperience.infra.service

import com.example.xperience.infra.exceptions.AuthenticationException
import com.example.xperience.infra.repository.UserRepository
import com.example.xperience.infra.security.UserDetailsImpl
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserDetailsService(
    private val userRepository: UserRepository
): UserDetailsService {
    override fun loadUserByUsername(input: String): UserDetails {
        val user = userRepository.findByUsername(input.replace("\\W".toRegex(), ""))
            ?: throw AuthenticationException("Usuário não encontrado.")
        return UserDetailsImpl(user)
    }
    fun loadById(personId: String): UserDetails {
        val user = userRepository.findById(UUID.fromString(personId)).orElseThrow()
        return UserDetailsImpl(user)
    }
}