package com.example.xperience.infra.security.filters

import com.example.xperience.infra.exceptions.AuthenticationException
import com.example.xperience.infra.security.JwtConfiguration
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

class AuthorizationFilter(
    authenticationManager: AuthenticationManager,
    private val jwtConfiguration: JwtConfiguration,
    private val userDetails: UserDetailsService,
) : BasicAuthenticationFilter(authenticationManager) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val authorization = request.getHeader("Authorization")
        if (authorization != null && authorization.startsWith("Bearer ")) {
            val auth = getAuthentication(authorization.split(" ")[1])
            SecurityContextHolder.getContext().authentication = auth
        }
        chain.doFilter(request, response)
    }

    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken {
        if (!jwtConfiguration.isValidToken(token)) {
            throw AuthenticationException("Token inválido")
        }
        val subject = jwtConfiguration.getSubject(token)
        val user = userDetails.loadById(subject)
        return UsernamePasswordAuthenticationToken(user, null, user.authorities)
    }
}