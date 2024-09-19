package com.abmike.ecommerce_prod_mgmt;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Test
public class ProductControllerTest {

    // Base URL of your API
    private static final String BASE_URL = "http://localhost:8082";

    @BeforeClass
    public void setup() {
        // Set up RestAssured base URI
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

    @DataProvider(name = "productData")
    public Object[][] productData() {
        return new Object[][] {
                {"New Product 1", "Description 1", 19.99, 100},
                {"New Product 2", "Description 2", 29.99, 50}
        };
    }

    @Test(dataProvider = "productData")
    public void testCreateProduct(String name, String description, double price, int stockQuantity) {
        String productJson = String.format("""
            {
                "name": "%s",
                "description": "%s",
                "price": %.2f,
                "stockQuantity": %d
            }
            """, name, description, price, stockQuantity);

        given()
                .contentType(ContentType.JSON)
                .body(productJson)
                .when()
                .post("/api/products")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("name", equalTo(name))
                .body("price", equalTo((float)price));
    }

    @Test
    public void testGetProductById() {
        // Assuming a product with ID 1 exists
        given()
                .pathParam("id", 28)
                .when()
                .get("/api/products/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1));
    }

    @Test
    public void testUpdateProduct() {
        // Assuming a product with ID 1 exists
        String updatedProductJson = """
            {
                "name": "Updated Product",
                "description": "Updated description",
                "price": 39.99,
                "stockQuantity": 75
            }
            """;

        given()
                .contentType(ContentType.JSON)
                .body(updatedProductJson)
                .pathParam("id", 28)
                .when()
                .put("/api/products/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("name", equalTo("Updated Product"))
                .body("price", equalTo(39.99f));
    }

    @Test
    public void testDeleteProduct() {
        // Assuming a product with ID 2 exists
        given()
                .pathParam("id", 28)
                .when()
                .delete("/api/products/{id}")
                .then()
                .statusCode(200);

        // Verify the product no longer exists
        given()
                .pathParam("id", 28)
                .when()
                .get("/api/products/{id}")
                .then()
                .statusCode(404);
    }
}