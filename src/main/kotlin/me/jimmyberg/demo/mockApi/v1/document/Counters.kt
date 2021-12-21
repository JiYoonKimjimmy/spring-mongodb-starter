package me.jimmyberg.demo.mockApi.v1.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("counters")
class Counters(
    @Id
    val id: String? = null,
    @Field("seq")
    val seq: Long
)