package io.github.mamachanko

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Web {

    @GetMapping("/api/shapes")
    fun shapes() : String {
        return "ok"
    }

}