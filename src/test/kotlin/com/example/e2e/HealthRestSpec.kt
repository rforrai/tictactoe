package com.example.e2e

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*
import io.restassured.RestAssured.*
import org.hamcrest.Matchers.*
import org.springframework.http.HttpStatus


class HealthRestSpec : Spek({


    describe("/health") {
        it("returns status OK") {
            get("/health")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("status", equalTo("OK"))
        }
    }
})