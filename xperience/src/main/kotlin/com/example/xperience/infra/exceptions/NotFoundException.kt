package com.example.xperience.infra.exceptions

class NotFoundException(
    override val message: String = "A solicitação não encontrou registro."
): Exception()
