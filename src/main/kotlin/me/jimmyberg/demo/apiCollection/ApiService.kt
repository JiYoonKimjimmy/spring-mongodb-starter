package me.jimmyberg.demo.apiCollection

import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ApiService(
    val mongoOperations: MongoOperations
) {

    fun getApi(request: Map<String, Any>): ResponseEntity<Map<String, Any>> {
        val query = Query().addCriteria(where("request").`is`(request))
        val api = mongoOperations.findOne(query, Api::class.java)
        return ResponseEntity.ok(api?.response)
    }

}