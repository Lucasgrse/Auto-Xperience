package com.example.xperience.infra.exceptions

class AuthorizationException(
    override val message: String = "Não autorizado.",
) : Exception()