package at.htl.cinema.business;

import at.htl.cinema.model.Cinema;
import at.htl.cinema.model.Employee;
import at.htl.cinema.model.Hall;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class InitBean {
    @Inject
    EntityManager em;

    @Transactional
    public void init(@Observes StartupEvent ev){
        System.err.println("------init------");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Cinema cineplexLinz = new Cinema("Cineplex Linz", "Prinz-Eugen-Straße 22, 4020 Linz", LocalDate.parse("1999-12-08", formatter));
        List<Hall> halls = new ArrayList<>();
        halls.add(new Hall("DOLBY CINEMA", 394, cineplexLinz));
        halls.add(new Hall("Saal 2", 133, cineplexLinz));
        halls.add(new Hall("Saal 3", 132, cineplexLinz));
        em.persist(cineplexLinz);
        for (Hall hall : halls) em.persist(hall);

        Cinema cineplexGraz = new Cinema("Cineplex Graz", "Alte Poststraße 470, 8055 Graz", LocalDate.parse("2004-06-17", formatter));
        halls = new ArrayList<>();
        em.persist(cineplexGraz);
        halls.add(new Hall("Saal 1", 247, cineplexGraz));
        halls.add( new Hall("Saal 2", 247, cineplexGraz));
        halls.add(new Hall("Saal 3", 184, cineplexGraz));
        em.persist(cineplexGraz);
        for (Hall hall : halls) em.persist(hall);


        //Panache
        Employee emp = new Employee(
                "Maxi",
                "Mustermann",
                2299,
                "12343",
                LocalDate.of(2005, Month.JANUARY,1),
                cineplexLinz
        );
        emp.persist();

        emp = new Employee(
                "Hubert",
                "Mustermann",
                1999,
                "12344",
                LocalDate.of(2006, Month.JANUARY,1),
                cineplexLinz
        );
        emp.persist();

        emp = new Employee(
                "Franz",
                "Schubert",
                2299,
                "12345",
                LocalDate.of(2005, Month.JANUARY,1),
                cineplexGraz
        );
        emp.persist();
        emp = new Employee(
                "Hans",
                "Schuster",
                2299,
                "12346",
                LocalDate.of(2005, Month.JANUARY,1),
                cineplexGraz
        );
        emp.persist();
    }
}
