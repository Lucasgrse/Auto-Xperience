package com.example.xperience.adapter.controller.dto

import jakarta.validation.constraints.NotEmpty

data class LoginRequestDTO(
    @field:NotEmpty(message = "E-mail, CPF, CNPJ ou Telefone devem ser informados.")
    val username: String,
    @field:NotEmpty(message = "Senha deve ser informada.")
    val password: String,
)