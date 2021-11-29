package me.jimmyberg.demo.apiCollection

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class ApiController(
    val apiService: ApiService
) {

    @PostMapping
    fun getApi(@RequestBody request: Map<String, Any>) = apiService.getApi(request)

}