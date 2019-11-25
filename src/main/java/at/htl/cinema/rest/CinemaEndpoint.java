package at.htl.cinema.rest;

import at.htl.cinema.database.CinemaFacade;
import at.htl.cinema.model.Cinema;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.persistence.PersistenceException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/cinema")
public class CinemaEndpoint {

    @Inject
    CinemaFacade cinemaFacde;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<Cinema> cinemas = cinemaFacde.getAll();
        return Response.ok(cinemas).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getById(@PathParam("id") long id) {
        Cinema c = cinemaFacde.getById(id);
        if(c != null){
            return Response.ok(c).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    @DELETE
    public Response deleteCinema(@PathParam("id")long id){
        Cinema c = cinemaFacde.getById(id);
        if(c != null){
            cinemaFacde.delete(c);
        }
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response addCinema(Cinema c) {
        try {
            c = cinemaFacde.insert(c);
           // cinemaFacde.insert(c);
        }catch(PersistenceException e){
            return Response.status(400).build();
        }
        return Response.ok(c).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateCinema(Cinema c) {
        c = cinemaFacde.update(c);
        return Response.ok(c).build();
    }
}