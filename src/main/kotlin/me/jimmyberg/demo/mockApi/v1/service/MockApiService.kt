package me.jimmyberg.demo.mockApi.v1.service

import me.jimmyberg.demo.config.EMPTY
import me.jimmyberg.demo.mockApi.v1.controller.MockApiController
import me.jimmyberg.demo.mockApi.v1.document.MockApi
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
class MockApiService(
    val mongoOperations: MongoOperations,
    val modelInfoService: ModelInfoService,
    val modelDataService: ModelDataService
) {

    // logger
    private val logger = LoggerFactory.getLogger(MockApiController::class.java)

    @Value("\${server.servlet.context-path}")
    private val contextPath = EMPTY

    fun request(httpServletRequest: HttpServletRequest, request: Map<String, Any>) = response(findMockApi(httpServletRequest), request)

    /**
     * Response 처리
     */
    private fun response(api: MockApi, request: Map<String, Any>) : ResponseEntity<Any> {
        return ResponseEntity.ok().body(
            when (api.apiMethod) {
                "GET" -> if (api.paging) {
                    findAll(api, request)
                } else {
                    findOne(api, request)
                }
                "POST" -> modelDataService.save(api.model, request)
                "PUT" -> modelDataService.modify(api, request)
                "DELETE" -> logger.info("Delete data")
                else -> EMPTY
            }
        )
    }

    /**
     * Mock api 정보 조회
     */
    private fun findMockApi(httpServletRequest: HttpServletRequest): MockApi {
        val url = if (contextPath != "/") httpServletRequest.requestURI.replace(contextPath, EMPTY) else httpServletRequest.requestURI
        val depths = url.split("/").filter { it.isNotEmpty() }
        val httpMethod = httpServletRequest.method
        logger.info("Request URL : $httpMethod $url")

        val query = query(where("url").regex(depths.joinToString("|")))
            .addCriteria(where("http_method").`is`(httpMethod))
            .addCriteria(where("depths").`is`(depths.size))

        val api = mongoOperations.findOne(query, MockApi::class.java)!!
        val apiUrl = api.url?.split("/")?.filter { it.isNotEmpty() }
        val apiRequest = api.request.toMutableMap()

        apiRequest.forEach { (key, type) ->
            val index = apiUrl?.indexOf("{$key}")!!
            if (index > -1) {
                val value = depths[index]
                apiRequest[key] = if (type == "number") value.toLong() else value
            }
        }
        api.request = apiRequest

        return api
    }

    /**
     * Mock API model data 다건 조회 with paginate
     */
    private fun findAll(api: MockApi, request: Map<String, Any>): MutableMap<String, Any> {
        // model info 조회
        val modelInfo = modelInfoService.findOneByTitle(api.model)

        val page = request["${api.paginate["page"]}"].toString().toInt()
        val pageSize = request["${api.paginate["pageSize"]}"].toString().toInt()
        val pageRequest = PageRequest.of(page, pageSize)
        
        // model data 다건 조회
        return modelDataService.findAll(api, modelInfo, pageRequest)
    }

    /**
     * Mock API model data 단건 조회
     */
    private fun findOne(api: MockApi, request: Map<String, Any>): MutableMap<String, Any> {
        // model info 조회
        val modelInfo = modelInfoService.findOneByTitle(api.model)

        // concat api request
        val params = request.toMutableMap()
        api.request.forEach { (key, value) -> params[key] = value }

        // model data 단건 조회
        return modelDataService.findOne(api, modelInfo, params)
    }

}