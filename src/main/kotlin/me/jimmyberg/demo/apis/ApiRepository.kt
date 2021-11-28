package me.jimmyberg.demo.apis

import org.springframework.data.mongodb.repository.MongoRepository

interface ApiRepository : MongoRepository<Api, String>