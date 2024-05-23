package com.ocunha.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfiguration {

    @Value("\${api.result.size:5}")
    lateinit var apiResultSize: String

    fun getApiResultSize(): Int {
        return apiResultSize.toInt()
    }
}