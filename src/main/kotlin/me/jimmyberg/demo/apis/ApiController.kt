package me.jimmyberg.demo.apis

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class ApiController(
    val apiService: ApiService
) {

    @GetMapping
    fun getApi() = apiService.getAll()

    @GetMapping("/one")
    fun getApi(@RequestParam request: MutableMap<String, Any>) = apiService.getOne(request)

}