package me.jimmyberg.demo.mockApi.v1.document

import me.jimmyberg.demo.mockApi.v1.model.PostModelInfoRequest
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("model_info")
class ModelInfo(
    @Id
    var id: String? = null,
    @Field("title")
    val title: String,
    @Field("seqName")
    val seqName: String,
    @Field("seqColumn")
    val seqColumn: String,
    @Field("columns")
    val columns: Map<String, Any> = mapOf()
) {
    companion object {
        fun ofRequest(request: PostModelInfoRequest) = ModelInfo(
            title = request.title,
            seqName = request.seqName,
            seqColumn = request.seqColumn,
            columns = request.columns
        )
    }
}