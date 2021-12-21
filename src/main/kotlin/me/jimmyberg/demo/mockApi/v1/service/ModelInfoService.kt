package me.jimmyberg.demo.mockApi.v1.service

import me.jimmyberg.demo.mockApi.v1.repository.ModelInfoRepository
import org.springframework.stereotype.Service

@Service
class ModelInfoService(
    val modelInfoRepository: ModelInfoRepository
) {

    /**
     * Model info 조회 by title
     */
    fun findOneByTitle(title: String) = modelInfoRepository.findByTitle(title)

}