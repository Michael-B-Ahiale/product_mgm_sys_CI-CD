package com.abmike.ecommerce_prod_mgmt.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryControllerTest {

    private static final String BASE_URL = "http://localhost:8082";
    private static List<Long> createdCategoryIds = new ArrayList<>();

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @Order(1)
    public void testGetAllCategories() {
        given()
                .when()
                .get("/api/categories")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", isA(List.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Electronics", "Books"})
    @Order(2)
    public void testCreateCategory(String baseName) {
        String uniqueName = baseName + "-" + UUID.randomUUID().toString().substring(0, 8);
        String categoryJson = String.format("""
            {
                "name": "%s"
            }
            """, uniqueName);

        Long categoryId = given()
                .contentType(ContentType.JSON)
                .body(categoryJson)
                .when()
                .post("/api/categories")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("name", equalTo(uniqueName))
                .extract()
                .jsonPath()
                .getLong("id");

        createdCategoryIds.add(categoryId);
    }

    @Test
    @Order(3)
    public void testGetCategoryById() {
        Long categoryId = createdCategoryIds.get(0);
        given()
                .pathParam("id", categoryId)
                .when()
                .get("/api/categories/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(categoryId.intValue()))
                .body("name", notNullValue());
    }

    @Test
    @Order(4)
    public void testUpdateCategory() {
        Long categoryId = createdCategoryIds.get(0);
        String updatedName = "Updated Category-" + UUID.randomUUID().toString().substring(0, 8);
        String updatedCategoryJson = String.format("""
            {
                "name": "%s"
            }
            """, updatedName);

        given()
                .contentType(ContentType.JSON)
                .body(updatedCategoryJson)
                .pathParam("id", categoryId)
                .when()
                .put("/api/categories/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("name", equalTo(updatedName));
    }

    @Test
    @Order(5)
    public void testDeleteCategory() {
        Long categoryId = createdCategoryIds.get(0);
        given()
                .pathParam("id", categoryId)
                .when()
                .delete("/api/categories/{id}")
                .then()
                .statusCode(200);

        // Verify the category no longer exists
        given()
                .pathParam("id", categoryId)
                .when()
                .get("/api/categories/{id}")
                .then()
                .statusCode(404);

        createdCategoryIds.remove(0);
    }

    @AfterAll
    public static void cleanup() {
        for (Long categoryId : createdCategoryIds) {
            given()
                    .pathParam("id", categoryId)
                    .when()
                    .delete("/api/categories/{id}")
                    .then()
                    .statusCode(200);
        }
        createdCategoryIds.clear();
    }
}