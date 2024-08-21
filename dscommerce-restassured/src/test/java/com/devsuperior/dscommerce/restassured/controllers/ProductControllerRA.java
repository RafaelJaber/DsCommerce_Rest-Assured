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
    private String productName;

    @BeforeEach
    public void setUp() {
        baseURI = "http://localhost:8080";

        productName = "Macbook Pro";
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

    @Test
    public void findAllShouldReturnPageProductsWhenNameIsEmpty() {
        given()
            .get("/products?page=0")
        .then()
            .statusCode(200)
                .body("content.name", hasItems("Macbook Pro", "PC Gamer Tera"));
    }

    @Test
    public void findAllShouldReturnPageProductsWhenNameIsNotEmpty() {
        given()
            .get("/products?name={productName}", productName)
        .then()
            .statusCode(200)
            .body("content.name", hasItem(productName));
    }

    @Test
    public void findAllShouldReturnPagedProductsWithPriceGreaterThan2000() {
        given()
            .get("/products?size=25")
        .then()
            .statusCode(200)
            .body("content.findAll { it.price > 2000 }.name", hasItems("Smart TV", "PC Gamer Max"));
    }
}
