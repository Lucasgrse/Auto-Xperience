package com.example.xperience.core.voter

interface Voter<I> {
    fun invoke(useCaseInput: I)
}