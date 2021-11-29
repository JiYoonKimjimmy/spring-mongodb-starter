package me.jimmyberg.demo.apiCollection

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "api")
data class Api(
    @Id
    val id: ObjectId? = null,
    val url: String = "",
    val request: Map<String, Any> = mapOf(),
    val response: Map<String, Any> = mapOf()
)