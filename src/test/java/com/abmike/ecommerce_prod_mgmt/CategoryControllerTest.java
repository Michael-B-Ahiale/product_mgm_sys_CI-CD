package com.abmike.ecommerce_prod_mgmt;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Test
public class CategoryControllerTest {

    private static final String BASE_URL = "http://localhost:8082";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void testGetAllCategories() {
        given()
                .when()
                .get("/api/categories")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(greaterThan(0)));  // Assumes response is a JSON array
    }

    @DataProvider(name = "categoryData")
    public Object[][] categoryData() {
        return new Object[][] {
                {"Electronics"},
                {"Books"}
        };
    }

    @Test(dataProvider = "categoryData")
    public void testCreateCategory(String name) {
        String categoryJson = String.format("""
            {
                "name": "%s"
            }
            """, name);

        given()
                .contentType(ContentType.JSON)
                .body(categoryJson)
                .when()
                .post("/api/categories")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("name", equalTo(name))
                ;
    }

    @Test
    public void testGetCategoryById() {
        // Assuming a category with ID 1 exists
        given()
                .pathParam("id", 1)
                .when()
                .get("/api/categories/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1));
    }

    @Test
    public void testUpdateCategory() {
        // Assuming a category with ID 1 exists
        String updatedCategoryJson = """
            {
                "name": "Updated Category",
                "description": "This category has been updated"
            }
            """;

        given()
                .contentType(ContentType.JSON)
                .body(updatedCategoryJson)
                .pathParam("id", 1)
                .when()
                .put("/api/categories/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("name", equalTo("Updated Category"))
                .body("description", equalTo("This category has been updated"));
    }

    @Test
    public void testDeleteCategory() {
        // Assuming a category with ID 2 exists
        given()
                .pathParam("id", 2)
                .when()
                .delete("/api/categories/{id}")
                .then()
                .statusCode(200);

        // Verify the category no longer exists
        given()
                .pathParam("id", 2)
                .when()
                .get("/api/categories/{id}")
                .then()
                .statusCode(404);
    }
}