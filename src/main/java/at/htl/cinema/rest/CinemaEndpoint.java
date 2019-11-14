package at.htl.cinema.rest;

import at.htl.cinema.model.Cinema;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/cinema")
public class CinemaEndpoint {

    @Inject
    EntityManager em;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response GetAll() {
        List<Cinema> cinemas = em.createNamedQuery("Cinema.findAll", Cinema.class).getResultList();
        return Response.ok(cinemas).build();
    }
}