package com.example.xperience.infra.security

import com.example.xperience.core.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

class UserDetailsImpl(private val user: User): UserDetails {

    val id: UUID = user.id!!

    override fun getAuthorities() = mutableListOf<GrantedAuthority>()

    override fun isEnabled() = true

    override fun getUsername() = user.username

    override fun isCredentialsNonExpired() = true

    override fun getPassword() = user.password

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true
}