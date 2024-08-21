package com.devsuperior.dscommerce.restassured.controllers;

import com.devsuperior.dscommerce.restassured.tests.TokenUtil;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@Tag("RA")
public class UserControllerRATests {

    private String clientUsername, clientPassword, adminUsername, adminPassword;
    private String adminToken, clientToken, invalidToken;

    @BeforeEach
    public void setup() throws JSONException {
        baseURI = "http://localhost:8080";

        clientUsername = "maria@gmail.com";
        clientPassword = "123456";
        adminUsername = "alex@gmail.com";
        adminPassword = "123456";

        clientToken = TokenUtil.obtainAccessToken(clientUsername, clientPassword);
        adminToken = TokenUtil.obtainAccessToken(adminUsername, adminPassword);
        invalidToken = adminToken + "xpto";
    }

    @Test
    public void getMeShouldReturnUserWhenAdminLogged() throws JSONException {
        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + adminToken)
                .accept(ContentType.JSON)
                .when()
                .get("/users/me")
                .then()
                .statusCode(200)
                .body("id", is(2))
                .body("name", equalTo("Alex Green"))
                .body("email", equalTo("alex@gmail.com"))
                .body("phone", equalTo("977777777"))
                .body("birthDate", equalTo("1987-12-13"))
                .body("roles", hasItems("ROLE_CLIENT", "ROLE_ADMIN"));
    }

    @Test
    public void getMeShouldReturnUserWhenClientLogged() throws JSONException {
        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + clientToken)
                .accept(ContentType.JSON)
                .when()
                .get("/users/me")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("name", equalTo("Maria Brown"))
                .body("email", equalTo("maria@gmail.com"))
                .body("phone", equalTo("988888888"))
                .body("birthDate", equalTo("2001-07-25"))
                .body("roles", hasItems("ROLE_CLIENT"));
    }

    @Test
    public void getMeShouldReturnUnauthorizedWhenInvalidToken() throws JSONException {
        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + invalidToken)
                .accept(ContentType.JSON)
                .when()
                .get("/users/me")
                .then()
                .statusCode(401);
    }
}
