package me.jimmyberg.demo.mockApi.beta

import me.jimmyberg.demo.config.EMPTY
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "mock_apis")
data class MockApi(
    @Id
    val id: String? = null,
    val url: String = EMPTY,
    val request: Map<String, Any> = mapOf(),
    val response: Map<String, Any> = mapOf()
)