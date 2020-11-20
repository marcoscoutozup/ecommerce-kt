package com.marcoscoutozup.ecommercekt.produto

import com.marcoscoutozup.ecommercekt.caracteristica.Caracteristica
import com.marcoscoutozup.ecommercekt.categoria.Categoria
import com.marcoscoutozup.ecommercekt.usuario.Usuario
import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import javax.validation.Valid
import javax.validation.constraints.*
import kotlin.collections.HashSet

@Entity
@Table(name = "tb_produto")
class Produto {

    @Id
    @GeneratedValue(generator = "uuid")
    val id: UUID? = null

    @NotBlank
    val nome: String

    @NotNull
    @Positive
    val valor: BigDecimal

    @NotNull
    @PositiveOrZero
    val quantidade: Int

    @NotBlank
    @Size(max = 1000)
    @Column(columnDefinition = "TEXT")
    val descricao: String

    @NotEmpty
    @ElementCollection
    @Valid
    val caracteristicas: Set<Caracteristica>

    @NotNull
    @ManyToOne
    val categoria: Categoria

    @NotNull
    @ManyToOne
    val vendedor: Usuario

    @ElementCollection
    var imagens: Set<String>

    @CreationTimestamp
    val criadoEm: LocalDateTime? = null

    constructor(nome: String, valor: BigDecimal, quantidade: Int, descricao: String, caracteristicas: Set<Caracteristica>, categoria: Categoria, vendedor: Usuario) {
        this.nome = nome
        this.valor = valor
        this.quantidade = quantidade
        this.descricao = descricao
        this.caracteristicas = caracteristicas
        this.categoria = categoria
        this.vendedor = vendedor
        this.imagens = HashSet()
    }

    fun adicionarImagensAoProduto(imagens: Set<String>){
        this.imagens = this.imagens.plus(imagens)
    }

    override fun toString(): String {
        return "Produto(id=$id, nome='$nome', valor=$valor, quantidade=$quantidade, descricao='$descricao', caracteristicas=$caracteristicas, categoria=$categoria, vendedor=$vendedor, imagens=$imagens, criadoEm=$criadoEm)"
    }

    fun verificarSeProdutoEDoVendedor(email: String): Boolean =
            this.vendedor.email == email


}