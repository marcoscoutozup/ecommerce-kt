package com.marcoscoutozup.ecommercekt.produto.adicionarimagem

import com.marcoscoutozup.ecommercekt.produto.CadastrarProdutoController
import com.marcoscoutozup.ecommercekt.produto.Produto
import com.marcoscoutozup.ecommercekt.seguranca.JwtUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.persistence.EntityManager
import javax.transaction.Transactional

@RestController
@RequestMapping("/produtos")
class AdicionarImagemController(val entityManager: EntityManager, val jwtUtils: JwtUtils) {

    val log: Logger = LoggerFactory.getLogger(CadastrarProdutoController::class.java)

    @PostMapping("/{idProduto}/adicionarimagem")
    @Transactional
    fun adicionarImagemNoProduto(@PathVariable idProduto: UUID,
                                 @RequestBody imagens: Imagem,
                                 @RequestHeader("Authorization") token: String): ResponseEntity<Any>{

        log.info("[INSERIR IMAGEM NO PRODUTO] Solicitação de inserção de imagem no produto")

        val email = jwtUtils.capturarEmailDoUsuarioLogado(token.substring(7))

        val produtoProcurado = Optional.ofNullable(entityManager.find(Produto::class.java, idProduto))

        if(produtoProcurado.isEmpty){
            log.info("[INSERIR IMAGEM NO PRODUTO] O produto não existe: {}", idProduto)
            var response: HashMap<String, String> = HashMap()
            response["message"] = "Produto não encontrado"
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response)
        }

        val produto = produtoProcurado.get()

        if(!produto.verificarSeProdutoEDoVendedor(email)){
            log.info("[INSERIR IMAGEM NO PRODUTO] O usuário não é o vendedor do produto: {}", idProduto)
            var response: HashMap<String, String> = HashMap()
            response["message"] = "Não autorizado - Usuário não pode fazer alterações no produto"
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response)
        }

        log.info("[INSERIR IMAGEM NO PRODUTO] Imagens inseridas no produto: {}", idProduto)
        produto.adicionarImagensAoProduto(imagens.imagens)
        entityManager.merge(produto)
        return ResponseEntity.ok().build();
    }
}