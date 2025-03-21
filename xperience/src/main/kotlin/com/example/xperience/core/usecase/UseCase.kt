package com.example.xperience.core.usecase

interface UseCase<I, O> {
    fun execute(input: I): O
}