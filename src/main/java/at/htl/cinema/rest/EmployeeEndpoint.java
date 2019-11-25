package at.htl.cinema.rest;

import at.htl.cinema.database.CinemaFacade;
import at.htl.cinema.model.Cinema;
import at.htl.cinema.model.Employee;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/employee")
public class EmployeeEndpoint {

    @Inject
    CinemaFacade cinemaFacade;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        List<Employee> employees = Employee.listAll();
        return Response.ok(employees).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getAllByCinemaId(@PathParam("id") Long id){
        List<Employee> employees = Employee.list("cinema.id", id);
        return Response.ok(employees).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public Response getEverything(){
        return Response.ok(Employee.find("select distinct e from Employee e left join fetch e.cinema")
                .list()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response addEmployee(@QueryParam("cinema_id")long cinemaId, Employee e) {
        try {
            Cinema c = cinemaFacade.getById(cinemaId);
            e.cinema = c;
            Employee.persist(e);
        }catch(PersistenceException ex){
            return Response.status(400).build();
        }
        return Response.ok(Employee.list("id",e.id).get(0)).build();
    }
}
