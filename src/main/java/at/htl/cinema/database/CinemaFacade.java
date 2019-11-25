package at.htl.cinema.database;

import at.htl.cinema.model.Cinema;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class CinemaFacade extends Facade<Cinema> {

    @Override
    public List<Cinema> getAll() {
        return em.createNamedQuery("Cinema.findAll", Cinema.class).getResultList();
    }

    @Override
    public Cinema getById(Long id) {
        return em.find(Cinema.class, id);
    }

    @Override
    public Cinema update(Cinema cinema) {
        return em.merge(cinema);
    }

    @Override
    public void delete(Cinema cinema) {
        em.remove(cinema);
    }
}
