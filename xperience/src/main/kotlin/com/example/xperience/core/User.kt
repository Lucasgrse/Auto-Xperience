package com.example.xperience.core

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "user")
data class User(

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    var id: UUID? = null,

    @Column(name = "email")
    var email: String,

    @Column(name = "password")
    var password: String? = null,

    @Column(name = "username")
    var username: String,
)