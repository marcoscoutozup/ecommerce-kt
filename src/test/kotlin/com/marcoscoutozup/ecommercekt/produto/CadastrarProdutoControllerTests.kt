package com.marcoscoutozup.ecommercekt.produto

import com.marcoscoutozup.ecommercekt.seguranca.JwtUtils
import com.marcoscoutozup.ecommercekt.usuario.Usuario
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import org.springframework.web.util.UriComponentsBuilder
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.TypedQuery

class CadastrarProdutoControllerTests {

    lateinit var entityManager: EntityManager
    lateinit var produtoRequest: ProdutoRequest
    lateinit var produto: Produto
    lateinit var controller: CadastrarProdutoController
    lateinit var jwtUtils: JwtUtils
    lateinit var usuario: Usuario
    lateinit var query: TypedQuery<*>

    @BeforeEach
    fun setup() {
        entityManager = mock(EntityManager::class.java)
        produtoRequest = mock(ProdutoRequest::class.java)
        produto = mock(Produto::class.java)
        jwtUtils = mock(JwtUtils::class.java)
        usuario = mock(Usuario::class.java)
        query = mock(TypedQuery::class.java)
        controller = CadastrarProdutoController(entityManager, jwtUtils)
    }

    @Test
    @DisplayName("Não deve cadastrar produto se vendedor não existir")
    fun naoDeveCadastrarProdutoSeVendedorNaoExistir() {
        `when`(entityManager.createNamedQuery(anyString(), eq(Usuario::class.java))).thenReturn(query as TypedQuery<Usuario>)
        `when`(query.setParameter(anyString(), any())).thenReturn(query)
        `when`(query.resultList).thenReturn(emptyList())
        `when`(jwtUtils.capturarEmailDoUsuarioLogado(anyString())).thenReturn(String())
        val response = controller.cadastrarNovoProduto(produtoRequest, retornaJwt(), UriComponentsBuilder.newInstance())
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertTrue(response.body is HashMap<*, *>)
    }

    @Test
    @DisplayName("Deve cadastrar produto")
    fun deveCadastrarUsuario() {
        `when`(produtoRequest.toProduto(usuario, entityManager)).thenReturn(produto)
        `when`(entityManager.createNamedQuery(anyString(), eq(Usuario::class.java))).thenReturn(query as TypedQuery<Usuario>)
        `when`(query.setParameter(anyString(), any())).thenReturn(query)
        `when`(query.resultList).thenReturn(listOf(usuario))
        val uuid = UUID.randomUUID()
        `when`(produto.id).thenReturn(uuid)
        `when`(jwtUtils.capturarEmailDoUsuarioLogado(anyString())).thenReturn(String())
        val response = controller.cadastrarNovoProduto(produtoRequest, retornaJwt(), UriComponentsBuilder.newInstance())
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertNotNull(response.headers["Location"])
    }

    fun retornaJwt() = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
}