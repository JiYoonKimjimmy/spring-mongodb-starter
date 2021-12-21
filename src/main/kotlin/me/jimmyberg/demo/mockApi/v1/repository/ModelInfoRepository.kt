package me.jimmyberg.demo.mockApi.v1.repository

import me.jimmyberg.demo.mockApi.v1.document.ModelInfo
import org.springframework.data.mongodb.repository.MongoRepository

interface ModelInfoRepository : MongoRepository<ModelInfo, String> {

    fun findByTitle(title: String): ModelInfo

}