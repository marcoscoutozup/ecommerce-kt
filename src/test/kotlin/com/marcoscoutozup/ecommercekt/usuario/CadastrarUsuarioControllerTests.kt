package com.marcoscoutozup.ecommercekt.usuario

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.http.HttpStatus
import org.springframework.web.util.UriComponentsBuilder
import java.util.*
import javax.persistence.EntityManager

class CadastrarUsuarioControllerTests {

    lateinit var entityManager: EntityManager
    lateinit var usuarioRequest: UsuarioRequest
    lateinit var usuario: Usuario
    lateinit var controller: CadastrarUsuarioController

    @BeforeEach
    fun setup() {
        entityManager = mock(EntityManager::class.java)
        usuarioRequest = mock(UsuarioRequest::class.java)
        usuario = mock(Usuario::class.java)
        controller = CadastrarUsuarioController(entityManager)
    }

    @Test
    @DisplayName("Deve cadastrar usuário")
    fun deveCadastrarUsuario() {
        `when`(usuarioRequest.toUsuario()).thenReturn(usuario)
        val uuid = UUID.randomUUID()
        `when`(usuario.id).thenReturn(uuid)
        val response = controller.cadastrarUsuario(usuarioRequest, UriComponentsBuilder.newInstance())
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertNotNull(response.headers["Location"])
    }

}