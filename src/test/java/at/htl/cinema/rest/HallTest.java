package at.htl.cinema.rest;


import at.htl.cinema.model.Cinema;
import at.htl.cinema.model.Hall;
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
public class HallTest {

    @Test
    public void testGetAllEndpoint() {
        given()
                .when().get("hall")
                .then()
                .statusCode(200);
    }

    @Test
    public void testInsertHall() {
        Cinema c = new Cinema("Test Kino", "address", LocalDate.now());

        Hall h = new Hall("Hall",50, c);
        Hall h2 = new Hall("Hall 2", 50, c);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            //insert cinema
            c.setId(given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ow.writeValueAsString(c))
                    .post("/cinema")
                    .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_JSON).extract().as(Cinema.class).getId());
            //insert Hall
            System.out.println(c.getId());
            System.out.println(ow.writeValueAsString(h));
            given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ow.writeValueAsString(h))
                    .post("/hall")
                    .then()
                    .statusCode(200);
            //insert Hall
            given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ow.writeValueAsString(h2))
                    .post("/hall")
                    .then()
                    .statusCode(200);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        given()
                .when().get("/hall/cinema/"+c.getId())
                .then()
                .statusCode(200)
                .assertThat().body("size", is(2));
    }

    @Test
    public void testUpdateHall(){
        Cinema c = new Cinema("Test Kino", "address", LocalDate.now());

        Hall h = new Hall("Hall",50, c);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            //insert cinema
            c.setId(given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ow.writeValueAsString(c))
                    .post("/cinema")
                    .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_JSON).extract().as(Cinema.class).getId());
            System.out.println(c.getId());
            System.out.println(ow.writeValueAsString(h));
            //insert Hall
            h = given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ow.writeValueAsString(h))
                    .post("/hall")
                    .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_JSON).extract().as(Hall.class);

            System.out.println(ow.writeValueAsString(h));
            h.setSeating(70);
            h = given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ow.writeValueAsString(h))
                    .put("/hall")
                    .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_JSON).extract().as(Hall.class);
            System.out.println(ow.writeValueAsString(h));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        assertThat(h.getSeating()).isEqualTo(70);

    }

    @Test
    public void DeleteHall(){
        Cinema c = new Cinema("Test Kino", "address", LocalDate.now());

        Hall h = new Hall("Hall 1",50, c);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        //insert cinema
        try {
            c.setId(given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ow.writeValueAsString(c))
                    .post("/cinema")
                    .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_JSON).extract().as(Cinema.class).getId());

            System.out.println(c.getId());
            System.out.println(ow.writeValueAsString(h));
            //insert Hall
            h = given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ow.writeValueAsString(h))
                    .post("/hall")
                    .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_JSON).extract().as(Hall.class);

            System.out.println(ow.writeValueAsString(h));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        given()
                .delete("/hall/"+h.getId()).then().statusCode(200);
        given()
                .delete("/cinema/"+c.getId()).then().statusCode(200);
    }

}
