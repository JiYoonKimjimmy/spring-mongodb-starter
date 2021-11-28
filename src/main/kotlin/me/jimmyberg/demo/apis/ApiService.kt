package me.jimmyberg.demo.apis

import org.springframework.stereotype.Service

@Service
class ApiService(
    val apiRepository: ApiRepository
) {

    fun getApi() {
        val apis = apiRepository.findAll()
        apis.forEach { println(it) }

    }

}