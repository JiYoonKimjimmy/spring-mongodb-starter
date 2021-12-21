package me.jimmyberg.demo.mockApi.beta

data class MockApiCollectionRequest(
    val url: String,
    val request: Map<String, Any>,
    val response: Map<String, Any>
)