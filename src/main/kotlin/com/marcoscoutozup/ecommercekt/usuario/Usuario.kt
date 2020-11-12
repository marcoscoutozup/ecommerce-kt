package com.marcoscoutozup.ecommercekt.usuario

import org.hibernate.annotations.CreationTimestamp
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.Email
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

@Entity
class Usuario {

    @Id
    @GeneratedValue(generator = "uuid")
    val id: UUID? = null

    @NotBlank
    @Email
    val email: String

    @NotBlank
    val senha: String

    @CreationTimestamp
    val criadoEm: LocalDateTime? = null

    constructor(email: String, senha: String) {
        this.email = email
        this.senha = criptografarSenha(senha)
    }

    fun criptografarSenha(senha: String): String = BCryptPasswordEncoder().encode(senha)

}