package me.jimmyberg.demo.mockApi.v1.model

data class PostModelDataListRequest(
    val modelDataList: MutableList<MutableMap<String, Any>>
)