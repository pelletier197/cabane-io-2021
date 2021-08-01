package com.qohash.cabaneio2021

import org.springframework.boot.Banner
import org.springframework.boot.SpringApplication
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication


@SpringBootApplication
class CabaneIo2021Application

fun main(args: Array<String>) {
    val app = SpringApplication(CabaneIo2021Application::class.java)
    app.setBannerMode(Banner.Mode.OFF)
    app.webApplicationType = WebApplicationType.NONE
    app.run(*args)
}
