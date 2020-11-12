package com.marcoscoutozup.ecommercekt.usuario

import javax.validation.constraints.Email
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

class UsuarioRequest (

        @NotBlank
        @Email
        val email: String,

        @NotBlank
        @Min(6)
        val senha: String
) {
                        //1
    fun toUsuario(): Usuario = Usuario(this.email, this.senha)

}
