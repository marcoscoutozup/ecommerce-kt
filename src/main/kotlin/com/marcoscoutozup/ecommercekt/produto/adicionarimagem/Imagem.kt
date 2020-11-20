package com.marcoscoutozup.ecommercekt.produto.adicionarimagem

import javax.validation.constraints.NotEmpty

class Imagem (@NotEmpty val imagens: Set<String>)