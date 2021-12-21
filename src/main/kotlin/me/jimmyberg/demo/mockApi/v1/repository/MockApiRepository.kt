package me.jimmyberg.demo.mockApi.v1.repository

import me.jimmyberg.demo.mockApi.v1.document.MockApi
import org.springframework.data.mongodb.repository.MongoRepository

interface MockApiRepository : MongoRepository<MockApi, String> {

    fun findByUrlAndHttpMethod(url: String, httpMethod: String): MockApi
    fun findByUrlAndDepthsAndHttpMethod(url: String, depths: Long, httpMethod: String): MockApi

}