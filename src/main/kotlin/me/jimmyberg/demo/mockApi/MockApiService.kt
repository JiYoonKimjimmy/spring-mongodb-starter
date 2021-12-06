package me.jimmyberg.demo.mockApi

import me.jimmyberg.demo.config.EMPTY
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class MockApiService(
    val mongoOperations: MongoOperations
) {

    @Value("\${server.servlet.context-path}")
    private val contextPath = EMPTY

    /**
     * mock api 조회 및 response 반환
     */
    fun getApi(url: String, request: Map<String, Any>?): ResponseEntity<Map<String, Any>> {
        // get query
        val query = Query()
            .addCriteria(where("url").`is`(url.replace(contextPath, EMPTY)))
            .addCriteria(where("request").`is`(request))

        // get mock api
        val mockApi = mongoOperations.findOne(query, MockApi::class.java)

        return ResponseEntity.ok().body(mockApi?.response)
    }

    /**
     * mock api 등록
     */
    fun postApi(request: List<MockApiCollectionRequest>) {
        request.forEach {
            mongoOperations.save(MockApi(url = it.url, request = it.request, response = it.response))
        }
    }

    /**
     * mock api 조회 by url
     */
    fun getAllApi(url: String?): ResponseEntity<List<MockApi>> =
        if (url?.isNotEmpty() == true) {
            ResponseEntity.ok().body(mongoOperations.find(query(where("url").`is`(url)), MockApi::class.java))
        } else {
            ResponseEntity.ok().body(mongoOperations.findAll(MockApi::class.java))
        }

    /**
     * mock api 수정
     */
    fun putApi(request: MockApiCollectionRequest) {

    }

    /**
     * mock api 삭제
     */
    fun deleteApi(id: String) {
        mongoOperations.remove(query(where("_id").`is`(id)), MockApi::class.java)
    }

}