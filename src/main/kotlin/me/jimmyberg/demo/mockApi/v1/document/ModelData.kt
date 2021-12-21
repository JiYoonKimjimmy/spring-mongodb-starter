package me.jimmyberg.demo.mockApi.v1.document

import me.jimmyberg.demo.config.EMPTY
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("model_data")
class ModelData(
    @Id
    var id: String? = null,
    @Field("model_id")
    val modelId: String = EMPTY,
    @Field("data")
    var data: Map<String, Any?> = mapOf()
) {
    companion object {
        fun toContent(modelData: ModelData, responseContent: Map<*, *>): MutableMap<*, *> {
            val data = mutableMapOf<String, Any?>()
            responseContent.forEach { (key, _) -> data["$key"] = modelData.data["$key"] }
            return data
        }
    }
}