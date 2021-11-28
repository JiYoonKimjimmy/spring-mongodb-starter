package me.jimmyberg.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PlayingMongoApplication

fun main(args: Array<String>) {
    runApplication<PlayingMongoApplication>(*args)
}
