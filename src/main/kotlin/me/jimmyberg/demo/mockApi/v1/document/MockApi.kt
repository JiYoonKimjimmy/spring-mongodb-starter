package me.jimmyberg.demo.mockApi.v1.document

import me.jimmyberg.demo.config.EMPTY
import me.jimmyberg.demo.config.ZERO
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "mock_api")
class MockApi(
    @Id
    var id: String? = null,
    @Field("url")
    val url: String? = EMPTY,
    @Field("depths")
    val depths: Long? = ZERO.toLong(),
    @Field("http_method")
    val httpMethod: String = EMPTY,
    @Field("api_method")
    val apiMethod: String = EMPTY,
    @Field("model")
    val model: String = EMPTY,
    @Field("paging")
    val paging: Boolean = false,
    @Field("paginate")
    val paginate: Map<String, Any> = mapOf(),
    @Field("request")
    var request: Map<String, Any> = mapOf(),
    @Field("response")
    val response: Map<String, Any> = mapOf()
)