package at.htl.cinema.rest;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class CinemaTest {

    @Test
    public void testGetAllEndpoint() {
        given()
          .when().get("cinema")
          .then()
             .statusCode(200)
             .body("size()", is(2))
        .body();
    }
}