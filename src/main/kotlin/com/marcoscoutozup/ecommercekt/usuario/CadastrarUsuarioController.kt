package com.marcoscoutozup.ecommercekt.usuario

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder
import javax.persistence.EntityManager
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
@RequestMapping("/usuarios")
class CadastrarUsuarioController(val entityManager: EntityManager){

    @PostMapping
    @Transactional                                          //1
    fun cadastrarUsuario(@RequestBody @Valid request:UsuarioRequest,
                         uri: UriComponentsBuilder): ResponseEntity<Any> {

                                //2
        var usuario = request.toUsuario()
        entityManager.persist(usuario);
        return ResponseEntity
                .created(uri.path("/usuarios/{id}")
                .buildAndExpand(usuario.id).toUri())
                .build()

    }

}
