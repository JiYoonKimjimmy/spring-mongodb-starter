package me.jimmyberg.demo.mockApi.v1.service

import me.jimmyberg.demo.mockApi.v1.document.Counters
import me.jimmyberg.demo.mockApi.v1.document.MockApi
import me.jimmyberg.demo.mockApi.v1.document.ModelData
import me.jimmyberg.demo.mockApi.v1.document.ModelInfo
import me.jimmyberg.demo.mockApi.v1.repository.ModelDataRepository
import me.jimmyberg.demo.mockApi.v1.repository.ModelInfoRepository
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.core.FindAndModifyOptions.options
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service

@Service
class ModelDataService(
    val mongoOperations: MongoOperations,
    val modelInfoRepository: ModelInfoRepository,
    val modelDataRepository: ModelDataRepository
) {
    // logger
    private val logger = LoggerFactory.getLogger(ModelDataService::class.java)

    /**
     * Mock API model data 다건 조회
     */
    fun findAll(api: MockApi, modelInfo: ModelInfo, pageRequest: PageRequest): MutableMap<String, Any> {
        // model data 다건 조회
        val pageable = modelDataRepository.findAllByModelId(modelInfo.id, pageRequest)

        // response 처리
        val response = api.response.toMutableMap()
        val responseData = (response["data"] as Map<*, *>).toMutableMap()
        val responseContent = responseData["content"] as Map<*, *>
        val content = pageable.content.map { ModelData.toContent(it, responseContent) }

        responseData["page"] = pageable.pageable.pageNumber
        responseData["pageSize"] = pageable.pageable.pageSize
        responseData["totalPages"] = pageable.totalPages
        responseData["totalElements"] = pageable.totalElements
        responseData["content"] = listOf(content)
        response["data"] = responseData

        return response
    }

    /**
     * Mock API model data 단건 조회
     */
    fun findOne(api: MockApi, modelInfo: ModelInfo, request: Map<String, Any>): MutableMap<String, Any> {
        // model data 단건 조회
        val query = Query()
        query.addCriteria(where("model_id").`is`(modelInfo.id))
        request.keys.forEach {
            query.addCriteria(where("data.$it").`is`(request[it]))
        }
        val modelData = mongoOperations.findOne(query, ModelData::class.java)

        val response = api.response.toMutableMap()

        response["data"] = modelData?.data as Any

        return response
    }

    /**
     * Mock API model data 저장
     */
    fun save(modelTitle: String, request: Map<String, Any>): Map<String, Any> {
        // model info 조회
        val modelInfo = modelInfoRepository.findByTitle(modelTitle)

        logger.info("Save data to api model $modelTitle")

        return if (modelInfo.id != null && modelInfo.title == modelTitle) {
            val sequence = generateSeq(modelInfo.seqName)

            val params = mutableMapOf<String, Any?>()
            modelInfo.columns.forEach { (key, _) ->
                params[key] = if (key == modelInfo.seqColumn) sequence else request[key]
            }
            logger.info("Request parameters : $params")

            val modelData = modelDataRepository.save(ModelData(modelId = modelInfo.id!!, data = params))

            mapOf("code" to "200", "message" to "OK", "data" to modelData.data["seq"].toString())
        } else {
            mapOf("code" to "500", "message" to "FAIL", "data" to 1)
        }
    }

    /**
     * Mock API model data 수정
     */
    fun modify(api: MockApi, request: Map<String, Any>): Map<String, Any> {
        val modelTitle = api.model

        // model info 조회
        val modelInfo = modelInfoRepository.findByTitle(modelTitle)

        val params = request.toMutableMap()
        api.request.forEach { (key, value) -> params[key] = (if (request[key] != null) request[key] else value) as Any }

        val seqColumn = modelInfo.seqColumn
        val modelDataSeq = params[seqColumn]
        logger.info("Modify data to api model $modelTitle [$modelDataSeq]")

        val query = query(where("model_id").`is`(modelInfo.id))
            .addCriteria(where("data.$seqColumn").`is`(modelDataSeq))
        val modelData = mongoOperations.findOne(query, ModelData::class.java)

        val newModelData = modelData?.data?.mapValues { params[it.key] }

        modelData?.data = newModelData!!

        modelDataRepository.save(modelData)

        logger.info(newModelData.toString())

        return mapOf("code" to "200", "message" to "OK", "data" to modelDataSeq!!)
    }

    /**
     * Model data sequence 조회
     */
    private fun generateSeq(counterId: String): Long? = mongoOperations.findAndModify(
        query(where("_id").`is`(counterId)),
        Update().inc("seq", 1),
        options().returnNew(true).upsert(true),
        Counters::class.java
    )?.seq

}