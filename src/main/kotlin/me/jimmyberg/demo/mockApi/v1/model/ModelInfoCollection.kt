package me.jimmyberg.demo.mockApi.v1.model

data class PostModelInfoRequest(
    val title: String,
    val seqName: String,
    val seqColumn: String,
    val columns: Map<String, Any>
)