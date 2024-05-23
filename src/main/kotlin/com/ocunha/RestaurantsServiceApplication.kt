package com.ocunha

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RestaurantsServiceApplication

fun main(args: Array<String>) {
	runApplication<RestaurantsServiceApplication>(*args)
}
