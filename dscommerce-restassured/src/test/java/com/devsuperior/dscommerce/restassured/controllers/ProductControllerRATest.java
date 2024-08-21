package com.devsuperior.dscommerce.restassured.controllers;

import com.devsuperior.dscommerce.restassured.tests.TokenUtil;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


@Tag("RA")
public class ProductControllerRATest {


    Faker faker = new Faker();

    private Long existingProductId, nonExistingProductId;
    private String productName;
    private String clientUsername, clientPassword, adminUsername, adminPassword;
    private String clientToken, adminToken, invalidToken;

    private Map<String, Object> postProductInstance;

    @BeforeEach
    public void setUp() {
        baseURI = "http://localhost:8080";

        productName = "Macbook Pro";
        clientUsername = "maria@gmail.com";
        clientPassword = "123456";
        adminUsername = "alex@gmail.com";
        adminPassword = "123456";

        clientToken = TokenUtil.obtainAccessToken(clientUsername, clientPassword);
        adminToken = TokenUtil.obtainAccessToken(adminUsername, adminPassword);
        invalidToken = adminToken + "xpto";

        postProductInstance = new HashMap<>();
        postProductInstance.put("name", faker.commerce().productName());
        postProductInstance.put("description", faker.lorem().sentence(50));
        postProductInstance.put("imageUrl", faker.avatar().image());
        postProductInstance.put("price", faker.commerce().price(500.0, 20000.50));

        List<Map<String, Object>> categories = new ArrayList<>();
        Map<String, Object> category1 = new HashMap<>();
        category1.put("id", 2);
        Map<String, Object> category2 = new HashMap<>();
        category2.put("id", 3);

        categories.add(category1);
        categories.add(category2);

        postProductInstance.put("categories", categories);
    }

    @Test
    public void findByIdShouldReturnProductWhenIdExists() {
        existingProductId = 2L;
        nonExistingProductId = 999L;

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

    @Test
    public void insertShouldReturnProductCreatedWhenAdminLogged() {
        JSONObject newProduct = new JSONObject(postProductInstance);
        given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .body(newProduct)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/products")
                .then()
                .statusCode(201)
                .body("name", equalTo(postProductInstance.get("name")))
                .body("price", is(Float.parseFloat(postProductInstance.get("price").toString())))
                .body("imageUrl", equalTo(postProductInstance.get("imageUrl")))
                .body("categories.id", hasItems(2, 3));
    }

    @Test
    public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndInvalidName() {
        postProductInstance.put("name", "ab");
        JSONObject newProduct = new JSONObject(postProductInstance);
        given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .body(newProduct)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/products")
                .then()
                .statusCode(422)
                .body("errors.fieldName", hasItem("name"))
                .body("errors.message", hasItem("Name must be 3 to 80 characters long"));
    }

    @Test
    public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndPriceIsNegative() {
        Double price = Double.parseDouble(faker.commerce().price(500.0, 20000.50)) * -1;
        postProductInstance.put("price", price);
        JSONObject newProduct = new JSONObject(postProductInstance);
        given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .body(newProduct)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/products")
                .then()
                .statusCode(422)
                .body("errors.fieldName", hasItem("price"))
                .body("errors.message", hasItem("The price must be positive"));
    }

    @Test
    public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndPriceIsZero() {
        Double price = 0.0;
        postProductInstance.put("price", price);
        JSONObject newProduct = new JSONObject(postProductInstance);
        given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .body(newProduct)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/products")
                .then()
                .statusCode(422)
                .body("errors.fieldName", hasItem("price"))
                .body("errors.message", hasItem("The price must be positive"));
    }

    @Test
    public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndProductHasNoCategory() {
        postProductInstance.put("categories", null);
        JSONObject newProduct = new JSONObject(postProductInstance);
        given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .body(newProduct)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/products")
                .then()
                .statusCode(422)
                .body("errors.fieldName", hasItem("categories"))
                .body("errors.message", hasItem("Must have minimum one category"));
    }

    @Test
    public void insertShouldReturnForbiddenWhenClientLogged() {
        JSONObject newProduct = new JSONObject(postProductInstance);
        given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + clientToken)
                .body(newProduct)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/products")
                .then()
                .statusCode(403);
    }

    @Test
    public void insertShouldReturnUnauthorizedWhenInvalidToken() {
        JSONObject newProduct = new JSONObject(postProductInstance);
        given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + invalidToken)
                .body(newProduct)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/products")
                .then()
                .statusCode(401);
    }

    @Test
    public void deleteShouldReturnNoContentWhenAdminLoggedAndExistingProductId() {
        existingProductId = createProduct();
        given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .accept(ContentType.JSON)
                .when()
                .delete("/products/" + existingProductId)
                .then()
                .statusCode(204);
    }

    @Test
    public void deleteShouldReturnNotFoundWhenAdminLoggedAndNonExistingProductId() {
        nonExistingProductId = 999L;
        given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .accept(ContentType.JSON)
                .when()
                .delete("/products/" + nonExistingProductId)
                .then()
                .statusCode(404);
    }

    @Test
    public void deleteShouldReturnForbiddenClientLoggedAndExistingProductId() {
        existingProductId = createProduct();
        given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + clientToken)
                .accept(ContentType.JSON)
                .when()
                .delete("/products/" + existingProductId)
                .then()
                .statusCode(403);
    }

    @Test
    public void deleteShouldReturnBadRequestAdminLoggedAndDependentProductId() {
        long dependentProductId = 3L;
        given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .accept(ContentType.JSON)
                .when()
                .delete("/products/" + dependentProductId)
                .then()
                .statusCode(400)
                .body("error", equalTo("Referential integrity failure"));
    }

    private Long createProduct() {
        JSONObject newProduct = new JSONObject(postProductInstance);
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .body(newProduct)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("/products");
        JsonPath jsonBody = response.jsonPath();
        String productId = jsonBody.getString("id");
        return Long.parseLong(productId);
    }
}
