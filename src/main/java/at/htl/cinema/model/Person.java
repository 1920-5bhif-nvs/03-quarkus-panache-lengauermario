package at.htl.cinema.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Person extends PanacheEntity {

    public String firstName;
    public String lastName;

    public Person(){
    }

    public Person(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
