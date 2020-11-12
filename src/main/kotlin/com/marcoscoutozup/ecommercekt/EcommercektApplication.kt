package com.marcoscoutozup.ecommercekt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = arrayOf(SecurityAutoConfiguration::class))
class EcommercektApplication

fun main(args: Array<String>) {
	runApplication<EcommercektApplication>(*args)
}
