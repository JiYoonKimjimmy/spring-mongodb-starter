package me.jimmyberg.demo.mockApi

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("API 요청 정보")
data class MockApiCollectionRequest(
    @ApiModelProperty("url")
    val url: String,
    @ApiModelProperty("요청 정보")
    val request: Map<String, Any>,
    @ApiModelProperty("응답 정보")
    val response: Map<String, Any>
)