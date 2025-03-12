package com.example.xperience.infra.security.filters

import com.example.xperience.adapter.controller.dto.LoginRequestDTO
import com.example.xperience.adapter.controller.dto.response.ErrorResponse
import com.example.xperience.infra.security.JwtConfiguration
import com.example.xperience.infra.security.UserDetailsImpl
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.swagger.v3.core.util.Json
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class AuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val jwtConfiguration: JwtConfiguration,
) : UsernamePasswordAuthenticationFilter(authenticationManager) {
    init {
        setFilterProcessesUrl("/users/login")
    }

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication? {

        val loginRequestDTO = jacksonObjectMapper().readValue(request.inputStream, LoginRequestDTO::class.java)
        val authToken = UsernamePasswordAuthenticationToken(loginRequestDTO.username, loginRequestDTO.password)
        return authenticationManager.authenticate(authToken)
    }

    override fun unsuccessfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        failed: AuthenticationException
    ) {
        val error = ErrorResponse(
            failed.message,
            null
        )

        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.outputStream.println(Json.pretty(error))
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val id = (authResult.principal as UserDetailsImpl).id
        val token = jwtConfiguration.generateToken(id)
        response.addHeader("Authorization", "Bearer $token")
        response.addHeader("userId", "$id")
    }
}