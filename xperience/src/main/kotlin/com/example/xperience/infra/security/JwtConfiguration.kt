package com.example.xperience.infra.security

import com.example.xperience.infra.exceptions.AuthenticationException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtConfiguration {
    @Value("\${jwt.expiration}")
    private val expiration: Long? = null

    @Value("\${jwt.secret}")
    private lateinit var secret: String

    private val key: Key by lazy {
        if(secret.length < 64){
            throw IllegalArgumentException("JWT secret key must be at least 64 characters long.")
        }
        Keys.hmacShaKeyFor(secret.toByteArray())
    }

    fun generateToken(id: UUID): String =
        Jwts
            .builder()
            .setSubject(id.toString())
            .setExpiration(Date(System.currentTimeMillis() + (expiration ?: 3600000)))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()

    fun isValidToken(token: String): Boolean {
        val claims = getClaims(token)
        return !(claims.subject == null || claims.expiration == null || Date().after(claims.expiration))
    }

    private fun getClaims(token: String): Claims {
        try {
            return Jwts
                .parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .body
        } catch (ex: Exception) {
            throw AuthenticationException("Token Inválido.")
        }
    }

    fun getSubject(token: String): String {
        if (!isValidToken(token)) {
            throw AuthenticationException("Token inválido.")
        }
        return getClaims(token).subject
    }
}