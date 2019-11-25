package at.htl.cinema.rest;

import at.htl.cinema.model.Cinema;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class CinemaTest {

    @Test
    public void testGetAllEndpoint() {
        given()
                .when().get("cinema")
                .then()
                .statusCode(200);
    }

    @Test
    public void testInsertCinema() {

        //Überprüfen wie viele Datensätze in der Datenbank sind.
        Response response = given()
                .when().get("cinema")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON).extract().response();
        int sizeBeforePut = response.body().path("list.size()");

        Cinema c = new Cinema("Test Kino", "address", LocalDate.now());
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        //Datensatz einfügen
        try {
            c = given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ow.writeValueAsString(c))
                    .post("/cinema")
                    .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_JSON).extract().as(Cinema.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        //Überprüfen ob jetzt um 1 Datensatz mehr in der Datenbank ist
        given()
                .when().get("cinema")
                .then()
                .statusCode(200)
                .body("size()", is(sizeBeforePut+1));
    }

    @Test
    public void testUpdateCinema(){
        Cinema c = new Cinema("Test Kino", "address", LocalDate.now());
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        try {
            c = given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ow.writeValueAsString(c))
                    .post("/cinema")
                    .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_JSON).extract().as(Cinema.class);

            c.setAddress("Musterstraße 20");
            c = given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ow.writeValueAsString(c))
                    .put("/cinema")
                    .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_JSON).extract().as(Cinema.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        assertThat(c.getAddress()).isEqualTo("Musterstraße 20");

    }

    @Test
    public void testDeleteCinema(){
        Cinema c = new Cinema("Test Kino", "address", LocalDate.now());
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        try {
            c = given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ow.writeValueAsString(c))
                    .post("/cinema")
                    .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_JSON).extract().as(Cinema.class);

            given()
                    .delete("/cinema/"+c.getId()).then().statusCode(200);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}