package com.abmike.ecommerce_prod_mgmt.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductControllerTest {

    private static final String BASE_URL = "http://localhost:8082";
    private static Long createdProductId;
    private static Long createdCategoryId;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @Order(1)
    public void testCreateCategory() {
        String categoryJson = """
            {
                "name": "Test Category"
            }
            """;

        createdCategoryId = given()
                .contentType(ContentType.JSON)
                .body(categoryJson)
                .when()
                .post("/api/categories")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("name", equalTo("Test Category"))
                .extract()
                .jsonPath()
                .getLong("id");
    }

    @Test
    @Order(2)
    public void testCreateProduct() {
        String productJson = String.format("""
            {
                "name": "Test Product",
                "description": "This is a test product",
                "price": 19.99,
                "category": {
                    "id": %d
                }
            }
            """, createdCategoryId);

        createdProductId = given()
                .contentType(ContentType.JSON)
                .body(productJson)
                .when()
                .post("/api/products")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("name", equalTo("Test Product"))
                .body("price", equalTo(19.99f))
                .body("category.id", equalTo(createdCategoryId.intValue()))
                .extract()
                .jsonPath()
                .getLong("id");
    }

    @Test
    @Order(3)
    public void testGetAllProducts() {
        given()
                .when()
                .get("/api/products")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("content", hasSize(greaterThan(0)))
                .body("content[0].id", notNullValue())
                .body("content[0].name", notNullValue())
                .body("content[0].description", notNullValue())
                .body("content[0].price", notNullValue())
                .body("content[0].category.id", notNullValue());
    }

    @Test
    @Order(4)
    public void testGetProductById() {
        given()
                .pathParam("id", createdProductId)
                .when()
                .get("/api/products/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(createdProductId.intValue()))
                .body("name", equalTo("Test Product"))
                .body("description", equalTo("This is a test product"))
                .body("price", equalTo(19.99f))
                .body("category.id", equalTo(createdCategoryId.intValue()));
    }

    @Test
    @Order(5)
    public void testUpdateProduct() {
        String updatedProductJson = String.format("""
            {
                "name": "Updated Product",
                "description": "This is an updated test product",
                "price": 29.99,
                "category": {
                    "id": %d
                }
            }
            """, createdCategoryId);

        given()
                .contentType(ContentType.JSON)
                .body(updatedProductJson)
                .pathParam("id", createdProductId)
                .when()
                .put("/api/products/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(createdProductId.intValue()))
                .body("name", equalTo("Updated Product"))
                .body("description", equalTo("This is an updated test product"))
                .body("price", equalTo(29.99f))
                .body("category.id", equalTo(createdCategoryId.intValue()));
    }

    @Test
    @Order(6)
    public void testPartialUpdateProduct() {
        String partialUpdateJson = """
            {
                "name": "Partially Updated Product",
                "price": 39.99
            }
            """;

        given()
                .contentType(ContentType.JSON)
                .body(partialUpdateJson)
                .pathParam("id", createdProductId)
                .when()
                .patch("/api/products/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(createdProductId.intValue()))
                .body("name", equalTo("Partially Updated Product"))
                .body("description", equalTo("This is an updated test product"))
                .body("price", equalTo(39.99f))
                .body("category.id", equalTo(createdCategoryId.intValue()));
    }

    @Test
    @Order(7)
    public void testSearchProducts() {
        given()
                .param("query", "Updated")
                .when()
                .get("/api/products/search")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("content", hasSize(greaterThan(0)))
                .body("content[0].name", containsString("Updated"));
    }

    @Test
    @Order(8)
    public void testGetProductsByCategory() {
        given()
                .pathParam("categoryId", createdCategoryId)
                .when()
                .get("/api/products/category/{categoryId}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("content", hasSize(greaterThan(0)))
                .body("content[0].category.id", equalTo(createdCategoryId.intValue()));
    }

    @Test
    @Order(9)
    public void testGetProductsInPriceRange() {
        given()
                .param("minPrice", 30.00)
                .param("maxPrice", 50.00)
                .when()
                .get("/api/products/price-range")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(greaterThan(0)))
                .body("[0].price", greaterThanOrEqualTo(30.00f))
                .body("[0].price", lessThanOrEqualTo(50.00f));
    }


    @Test
    @Order(11)
    public void testDeleteProduct() {
        given()
                .pathParam("id", createdProductId)
                .when()
                .delete("/api/products/{id}")
                .then()
                .statusCode(200);

        given()
                .pathParam("id", createdProductId)
                .when()
                .get("/api/products/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(12)
    public void testCleanup() {
        // Delete the category
        given()
                .pathParam("id", createdCategoryId)
                .when()
                .delete("/api/categories/{id}")
                .then()
                .statusCode(200);

        // Verify the category no longer exists
        given()
                .pathParam("id", createdCategoryId)
                .when()
                .get("/api/categories/{id}")
                .then()
                .statusCode(404);
    }
}