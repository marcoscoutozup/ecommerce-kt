package com.marcoscoutozup.ecommercekt.produto

import com.marcoscoutozup.ecommercekt.caracteristica.Caracteristica
import com.marcoscoutozup.ecommercekt.categoria.Categoria
import com.marcoscoutozup.ecommercekt.produto.adicionarimagem.AdicionarImagemController
import com.marcoscoutozup.ecommercekt.produto.adicionarimagem.Imagem
import com.marcoscoutozup.ecommercekt.seguranca.JwtUtils
import com.marcoscoutozup.ecommercekt.usuario.Usuario
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks
import java.math.BigDecimal
import javax.persistence.EntityManager

class ProdutoTests {

    @Mock
    lateinit var entityManager: EntityManager

    @Mock
    lateinit var caracteristica: Caracteristica

    @Mock
    lateinit var categoria: Categoria

    @Mock
    lateinit var vendedor: Usuario

    @Mock
    lateinit var jwtUtils: JwtUtils

    lateinit var controller: AdicionarImagemController

    @BeforeEach
    fun setup() {
        initMocks(this)
        controller = AdicionarImagemController(entityManager, jwtUtils)
    }

    @Test
    @DisplayName("Deve adicionar imagens no produto")
    fun deveAdicionarImagensNoProduto(){
        val imagens = Imagem(setOf(String()))
        val produto = Produto(String(), BigDecimal.ONE, 1, String(), setOf(caracteristica), categoria, vendedor)
        produto.adicionarImagensAoProduto(imagens.imagens)
        assertTrue(produto.imagens.isNotEmpty())

    }

    @Test
    @DisplayName("Produto é do vendedor")
    fun produtoEDoVendedor() {
        val vendedor = Usuario("email@email.com", String())
        val produto = Produto(String(), BigDecimal.ONE, 1, String(), setOf(caracteristica), categoria, vendedor)
        val result = produto.verificarSeProdutoEDoVendedor("email@email.com")
        assertTrue(result)
    }

    @Test
    @DisplayName("Produto não é do vendedor")
    fun produtoNaoEDoVendedor() {
        val vendedor = Usuario("email2@email.com", String())
        val produto = Produto(String(), BigDecimal.ONE, 1, String(), setOf(caracteristica), categoria, vendedor)
        val result = produto.verificarSeProdutoEDoVendedor("email@email.com")
        assertFalse(result)
    }
}