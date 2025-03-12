package com.example.xperience.infra.repository

import com.example.xperience.core.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository: JpaRepository<User, UUID> {

    fun findByUsername(replace: String): User?
}