package me.jimmyberg.demo.apis

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ApiService(
    val apiRepository: ApiRepository
) {

    fun getAll() = ResponseEntity.ok().body(apiRepository.findAll())

    fun getOne(request: MutableMap<String, Any>) {
        println(request)
    }

}