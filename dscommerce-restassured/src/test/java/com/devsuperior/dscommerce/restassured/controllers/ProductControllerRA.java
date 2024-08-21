package com.devsuperior.dscommerce.restassured.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

@Tag("RA")
public class ProductControllerRA {

    private Long existingProductId, nonExistingProductId;

    @BeforeEach
    public void setUp() {
        baseURI = "http://localhost:8080";

    }

    @Test
    public void findByIdShouldReturnProductWhenIdExists() {
        existingProductId = 2L;

        given()
            .get("/products/" + existingProductId)
        .then()
            .statusCode(200)
            .body("id", is(existingProductId.intValue()))
            .body("name", equalTo("Smart TV"))
            .body("price", is(2190.0F))
            .body("categories.id", hasItems(2, 3))
            .body("categories.name", hasItems("Eletrônicos", "Computadores"));
    }
}
