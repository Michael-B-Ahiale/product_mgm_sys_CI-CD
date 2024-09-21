package com.abmike.ecommerce_prod_mgmt.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ProductControllerTest {

    private static final String BASE_URL = "http://localhost:8082";

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void testGetAllProducts() {
        given()
                .when()
                .get("/api/products")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("content", hasSize(greaterThan(0)));
    }

//    @ParameterizedTest
//    @CsvSource({
//            "New Product 1, Description 1, 19.99, 100",
//            "New Product 2, Description 2, 29.99, 50"
//    })
//    public void testCreateProduct(String name, String description, double price, int stockQuantity) {
//        String productJson = String.format("""
//            {
//                "name": "%s",
//                "description": "%s",
//                "price": %.2f,
//                "stockQuantity": %d
//            }
//            """, name, description, price, stockQuantity);
//
//        given()
//                .contentType(ContentType.JSON)
//                .body(productJson)
//                .when()
//                .post("/api/products")
//                .then()
//                .statusCode(200)
//                .contentType(ContentType.JSON)
//                .body("name", equalTo(name))
//                .body("price", equalTo((float)price));
//    }
//
//    @Test
//    public void testGetProductById() {
//        given()
//                .pathParam("id", 28)
//                .when()
//                .get("/api/products/{id}")
//                .then()
//                .statusCode(200)
//                .contentType(ContentType.JSON)
//                .body("id", equalTo(28));
//    }
//
//    @Test
//    public void testUpdateProduct() {
//        String updatedProductJson = """
//            {
//                "name": "Updated Product",
//                "description": "Updated description",
//                "price": 39.99,
//                "stockQuantity": 75
//            }
//            """;
//
//        given()
//                .contentType(ContentType.JSON)
//                .body(updatedProductJson)
//                .pathParam("id", 28)
//                .when()
//                .put("/api/products/{id}")
//                .then()
//                .statusCode(200)
//                .contentType(ContentType.JSON)
//                .body("name", equalTo("Updated Product"))
//                .body("price", equalTo(39.99f));
//    }
//
//    @Test
//    public void testDeleteProduct() {
//        given()
//                .pathParam("id", 28)
//                .when()
//                .delete("/api/products/{id}")
//                .then()
//                .statusCode(200);
//
//        given()
//                .pathParam("id", 28)
//                .when()
//                .get("/api/products/{id}")
//                .then()
//                .statusCode(404);
//    }
}