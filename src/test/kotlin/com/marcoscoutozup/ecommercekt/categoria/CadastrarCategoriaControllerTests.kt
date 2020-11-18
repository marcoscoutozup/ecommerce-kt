package com.marcoscoutozup.ecommercekt.categoria

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

class CadastrarCategoriaControllerTests {

    lateinit var entityManager: EntityManager
    lateinit var categoriaRequest: CategoriaRequest
    lateinit var categoria: Categoria
    lateinit var controller: CadastrarCategoriaController

    @BeforeEach
    fun setup(){
        entityManager = mock(EntityManager::class.java)
        categoriaRequest = mock(CategoriaRequest::class.java)
        categoria = mock(Categoria::class.java)
        controller = CadastrarCategoriaController(entityManager)
    }

    @Test
    @DisplayName("Deve cadastrar categoria")
    fun deveCadastrarCategoria() {
        `when`(categoriaRequest.toCategoria(entityManager)).thenReturn(categoria)
        val uuid = UUID.randomUUID()
        `when`(categoria.id).thenReturn(uuid)
        val response = controller.cadastrarCategoria(categoriaRequest, UriComponentsBuilder.newInstance())
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertNotNull(response.headers["Location"])
    }

}