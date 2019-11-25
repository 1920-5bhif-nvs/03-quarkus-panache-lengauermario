package at.htl.cinema.rest;

import at.htl.cinema.database.HallFacade;
import at.htl.cinema.model.Hall;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/hall")
public class HallEndpoint {

    @Inject
    HallFacade hallFacade;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<Hall> halls = hallFacade.getAll();
        return Response.ok(halls).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addHall(Hall h) {
        hallFacade.insert(h);
        return Response.noContent().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response updateCinema(@PathParam("id")int id, Hall h) {
        hallFacade.update(h);
        return Response.noContent().build();
    }
}