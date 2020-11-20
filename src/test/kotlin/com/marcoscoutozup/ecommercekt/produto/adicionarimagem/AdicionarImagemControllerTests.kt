package com.marcoscoutozup.ecommercekt.produto.adicionarimagem

import com.marcoscoutozup.ecommercekt.produto.Produto
import com.marcoscoutozup.ecommercekt.seguranca.JwtUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks
import org.springframework.http.HttpStatus
import java.util.*
import javax.persistence.EntityManager

class AdicionarImagemControllerTests {

    @Mock
    lateinit var entityManager: EntityManager

    @Mock
    lateinit var jwtUtils: JwtUtils

    @Mock
    lateinit var produto: Produto

    lateinit var adicionarImagemController: AdicionarImagemController

    @BeforeEach
    fun setup(){
        initMocks(this)
        adicionarImagemController = AdicionarImagemController(entityManager, jwtUtils)
    }

    @Test
    @DisplayName("Não deve adicionar imagens se o produto não existe")
    fun naoDeveAdicionarImagensSeOProdutoNaoExiste(){
        `when`(jwtUtils.capturarEmailDoUsuarioLogado(anyString())).thenReturn(String())
        `when`(entityManager.find(eq(Produto::class.java), any())).thenReturn(null)
        val response = adicionarImagemController.adicionarImagemNoProduto(UUID.randomUUID(), Imagem(setOf(String())), retornaJwt())
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertTrue(response.body is HashMap<*, *>)
    }

    @Test
    @DisplayName("Não deve adicionar imagens se o produto não for do vendedor")
    fun naoDeveAdicionarImagensSeOProdutoNaoForDoVendedor(){
        `when`(jwtUtils.capturarEmailDoUsuarioLogado(anyString())).thenReturn(String())
        `when`(entityManager.find(eq(Produto::class.java), any())).thenReturn(produto)
        `when`(produto.verificarSeProdutoEDoVendedor(anyString())).thenReturn(false)
        val response = adicionarImagemController.adicionarImagemNoProduto(UUID.randomUUID(), Imagem(setOf(String())), retornaJwt())
        assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)
        assertTrue(response.body is HashMap<*, *>)
    }

    @Test
    @DisplayName("Deve adicionar imagens ao produto")
    fun deveAdicionarImagensAoProduto(){
        `when`(jwtUtils.capturarEmailDoUsuarioLogado(anyString())).thenReturn(String())
        `when`(entityManager.find(eq(Produto::class.java), any())).thenReturn(produto)
        `when`(produto.verificarSeProdutoEDoVendedor(anyString())).thenReturn(true)
        val response = adicionarImagemController.adicionarImagemNoProduto(UUID.randomUUID(), Imagem(setOf(String())), retornaJwt())
        assertEquals(HttpStatus.OK, response.statusCode)
    }

    fun retornaJwt() = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"


}