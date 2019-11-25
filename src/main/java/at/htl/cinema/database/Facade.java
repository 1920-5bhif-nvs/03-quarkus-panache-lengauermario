package at.htl.cinema.database;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

abstract class Facade<T> {
    @Inject
    EntityManager em;

    public T insert(T t) {
        em.persist(t);
        em.refresh(t);
        return t;
    }

    public abstract List<T> getAll();

    public abstract T getById(Long id);

    public abstract T update(T t);

    public abstract void delete(T t);
}
