package at.htl.cinema.rest;

import at.htl.cinema.model.Cinema;
import at.htl.cinema.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.time.Month;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class EmployeeTest {

    @Test
    public void testGetEmployeeFromCinema(){

        //Cinema einfügen
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
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //Employee einfügen

        Employee employee = new Employee("Maxi", "Mustermann", 2000, "1234", LocalDate.of(2013, Month.APRIL, 1));
        try {
            System.out.println(ow.writeValueAsString(employee));
            employee = given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ow.writeValueAsString(employee))
                    .post("/employee?cinema_id="+c.getId())
                    .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_JSON).extract().as(Employee.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        //GetEmployee from Cinema

        given()
                .when().get("employee/"+c.getId())
                .then()
                .statusCode(200)
                .body("size()", is(1));
    }
}
