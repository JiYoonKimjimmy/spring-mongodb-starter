package me.jimmyberg.demo.apiCollection

import org.springframework.data.mongodb.repository.MongoRepository

interface ApiRepository : MongoRepository<Api, String>